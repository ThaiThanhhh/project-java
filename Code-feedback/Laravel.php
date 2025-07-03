// routes/api.php
Route::post('/ask', 'AiController@ask');

// app/Http/Controllers/AiController.php
namespace App\Http\Controllers;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;

class AiController extends Controller
{
    public function ask(Request $req)
    {
        $q = $req->input('question');
        $pid = $req->input('productId');
        $products = [
            'pb1' => [
                'name' => 'Vợt Pickleball Pro X',
                'description' => 'Vợt cao cấp với lõi graphite và bề mặt textured.',
                'specs' => '240g, 16x8 in, cân bằng trung tâm.'
            ],
        ];
        $p = $products[$pid] ?? $products['pb1'];
        
        $system = "
Bạn là trợ lý bán hàng cho sản phẩm pickleball:
Tên: {$p['name']}
Mô tả: {$p['description']}
Thông số kỹ thuật: {$p['specs']}
Hãy trả lời ngắn gọn, rõ ràng và thân thiện.
";
        $res = Http::withToken(env('OPENAI_API_KEY'))
            ->post('https://api.openai.com/v1/chat/completions', [
                'model' => 'gpt-3.5-turbo',
                'messages' => [
                    ['role'=>'system','content'=> $system],
                    ['role'=>'user','content'=> $q]
                ],
                'temperature' => 0.7
            ]);
        
        $ans = $res->json('choices.0.message.content') 
               ?? 'Xin lỗi, không thể trả lời.';
        
        return response()->json(['answer'=>$ans]);
    }
}
