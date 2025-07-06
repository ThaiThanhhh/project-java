import cv2
import mediapipe as mp
import numpy as np
from collections import defaultdict

mp_pose = mp.solutions.pose
mp_drawing = mp.solutions.drawing_utils

# Database of advanced poses and techniques
TECHNIQUE_DB = {
    "ready_stance": {
        "description": "Ready stance",
        "key_points": {
            "knee_angle": (120, 150),
            "elbow_angle": (80, 120),
            "balance_threshold": 0.15,
            "shoulder_hip_alignment": True,
            "weight_distribution": (45, 55)  # Left/right weight distribution
        },
        "common_mistakes": [
            "knee_straight",
            "elbow_straight",
            "poor_balance",
            "feet_too_wide",
            "weight_back"
        ]
    },
    "serve": {
        "description": "Serve",
        "key_points": {
            "wrist_below_hip": True,
            "elbow_angle": (160, 180),
            "knee_angle": (120, 150),
            "ball_contact_height": (0.7, 1.0),
            "backswing_angle": (30, 60),
            "follow_through": True
        },
        "common_mistakes": [
            "serve_wrist_flip",
            "serve_ball_toss",
            "serve_foot_fault",
            "serve_early_rotation"
        ]
    },
    "forehand": {
        "description": "Forehand",
        "key_points": {
            "elbow_angle": (30, 90),
            "shoulder_elbow_wrist_alignment": "open",
            "weight_transfer": True,
            "hip_rotation": (30, 60),
            "follow_through": True
        },
        "common_mistakes": [
            "forehand_grip_too_tight",
            "forehand_late_preparation",
            "forehand_no_followthrough"
        ]
    },
    "backhand": {
        "description": "Backhand",
        "key_points": {
            "elbow_angle": (30, 90),
            "shoulder_elbow_wrist_alignment": "closed",
            "weight_transfer": True,
            "grip_type": "eastern",
            "stance_width": "shoulder_width"
        },
        "common_mistakes": [
            "backhand_weak_grip",
            "backhand_elbow_drop",
            "backhand_no_rotation"
        ]
    },
    "volley": {
        "description": "Volley",
        "key_points": {
            "elbow_angle": (60, 100),
            "short_backswing": True,
            "knee_angle": (120, 150),
            "wrist_firmness": "firm",
            "contact_point": "in_front"
        },
        "common_mistakes": [
            "volley_swinging",
            "volley_lazy_footwork",
            "volley_soft_wrist"
        ]
    }
}

# Error details and advanced corrections
ERROR_DB = {
    "knee_straight": {
        "description": "Straight knees",
        "harm": "Reduces mobility and reflexes, increases injury risk",
        "correction": "Lower your center of gravity, bend knees 120-150°, keep back straight",
        "severity": "high",
        "visual_cue": "Knees nearly vertical, no bend angle",
        "drills": [
            "Practice squat holds for 30 seconds",
            "Side steps with bent knees"
        ]
    },
    "elbow_straight": {
        "description": "Straight elbows",
        "harm": "Reduces power and control, increases elbow injury risk",
        "correction": "Keep elbows bent 80-120°, relax arm muscles",
        "severity": "medium",
        "visual_cue": "Arm fully extended during swing",
        "drills": [
            "Practice swings with resistance band",
            "Light ball hitting focusing on elbow angle"
        ]
    },
    "poor_balance": {
        "description": "Poor balance",
        "harm": "Reduces accuracy and stability, harder to recover position",
        "correction": "Distribute weight evenly, keep center of gravity low",
        "severity": "high",
        "visual_cue": "Body leaning to one side or unstable feet",
        "drills": [
            "Single-leg stands with eyes closed",
            "Side-to-side balance movements"
        ]
    },
    "serve_wrist_flip": {
        "description": "Wrist flip during serve",
        "harm": "Uncontrolled ball spin, reduces accuracy",
        "correction": "Keep wrist firm, use arm and shoulder for power",
        "severity": "medium",
        "visual_cue": "Excessive wrist bend at ball contact",
        "drills": [
            "Practice serve motion without ball, focus on arm movement",
            "Serve with ball balanced on paddle"
        ]
    },
    "forehand_grip_too_tight": {
        "description": "Too tight forehand grip",
        "harm": "Reduces power and control, causes hand fatigue",
        "correction": "Hold paddle with moderate pressure (3/10), relax wrist",
        "severity": "low",
        "visual_cue": "White knuckles from gripping too tight",
        "drills": [
            "Practice swings with towel wrapped around handle",
            "Light ball hitting with moderate grip pressure"
        ]
    },
    "backhand_weak_grip": {
        "description": "Weak backhand grip",
        "harm": "Paddle rotates at contact, reduces power",
        "correction": "Adjust to eastern backhand grip, increase grip pressure",
        "severity": "medium",
        "visual_cue": "Paddle face too open at contact",
        "drills": [
            "Practice backhand swings with resistance band",
            "Slow ball hitting focusing on grip"
        ]
    },
    "volley_swinging": {
        "description": "Excessive swing during volley",
        "harm": "Loss of control, reduces accuracy",
        "correction": "Keep backswing short, use legs and hips for power",
        "severity": "medium",
        "visual_cue": "Long backswing past shoulder during volley",
        "drills": [
            "Practice volleys with backswing under 30cm",
            "Practice volleys from close distance"
        ]
    }
}

def calculate_angle(a, b, c):
    a, b, c = np.array(a), np.array(b), np.array(c)
    radians = np.arctan2(c[1]-b[1], c[0]-b[0]) - np.arctan2(a[1]-b[1], a[0]-b[0])
    angle = np.abs(radians * 180.0 / np.pi)
    return 360 - angle if angle > 180 else angle

def draw_technique_guidelines(image, landmarks, technique):
    """Draw technique guidelines on the image"""
    # Draw basic landmarks
    mp_drawing.draw_landmarks(
        image, landmarks, mp_pose.POSE_CONNECTIONS,
        mp_drawing.DrawingSpec(color=(245,117,66), thickness=2, circle_radius=2),
        mp_drawing.DrawingSpec(color=(245,66,230), thickness=2, circle_radius=2)
    )
    
    # Add technique-specific guidelines
    if technique == "serve":
        # Draw serve guideline
        wrist = [landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value].x,
                 landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value].y]
        hip = [landmarks[mp_pose.PoseLandmark.RIGHT_HIP.value].x,
               landmarks[mp_pose.PoseLandmark.RIGHT_HIP.value].y]
        cv2.line(image, 
                tuple(np.multiply(wrist, [image.shape[1], image.shape[0]]).astype(int)),
                tuple(np.multiply(hip, [image.shape[1], image.shape[0]]).astype(int)),
                (0, 255, 0), 2)
    
    elif technique == "forehand":
        # Draw forehand swing guideline
        shoulder = [landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value].x,
                    landmarks[mp_pose.PoseLandmark.RIGHT_SHOULDER.value].y]
        elbow = [landmarks[mp_pose.PoseLandmark.RIGHT_ELBOW.value].x,
                 landmarks[mp_pose.PoseLandmark.RIGHT_ELBOW.value].y]
        wrist = [landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value].x,
                 landmarks[mp_pose.PoseLandmark.RIGHT_WRIST.value].y]
        
        points = np.array([shoulder, elbow, wrist]) * [image.shape[1], image.shape[0]]
        cv2.polylines(image, [points.astype(int)], isClosed=False, color=(0, 255, 255), thickness=2)
    
    return image

def analyze_technique(landmarks):
    """Perform detailed technique analysis"""
    get = lambda idx: [landmarks[idx].x, landmarks[idx].y]
    
    # Get key points
    s_l = get(mp_pose.PoseLandmark.LEFT_SHOULDER.value)
    s_r = get(mp_pose.PoseLandmark.RIGHT_SHOULDER.value)
    e_l = get(mp_pose.PoseLandmark.LEFT_ELBOW.value)
    e_r = get(mp_pose.PoseLandmark.RIGHT_ELBOW.value)
    w_l = get(mp_pose.PoseLandmark.LEFT_WRIST.value)
    w_r = get(mp_pose.PoseLandmark.RIGHT_WRIST.value)
    h_l = get(mp_pose.PoseLandmark.LEFT_HIP.value)
    h_r = get(mp_pose.PoseLandmark.RIGHT_HIP.value)
    k_l = get(mp_pose.PoseLandmark.LEFT_KNEE.value)
    k_r = get(mp_pose.PoseLandmark.RIGHT_KNEE.value)
    a_l = get(mp_pose.PoseLandmark.LEFT_ANKLE.value)
    a_r = get(mp_pose.PoseLandmark.RIGHT_ANKLE.value)
    f_l = get(mp_pose.PoseLandmark.LEFT_FOOT_INDEX.value)
    f_r = get(mp_pose.PoseLandmark.RIGHT_FOOT_INDEX.value)

    # Calculate angles and parameters
    elbow_l = calculate_angle(s_l, e_l, w_l)
    elbow_r = calculate_angle(s_r, e_r, w_r)
    knee_l = calculate_angle(h_l, k_l, a_l)
    knee_r = calculate_angle(h_r, k_r, a_r)
    shoulder_hip_angle_l = calculate_angle(s_l, h_l, k_l)
    shoulder_hip_angle_r = calculate_angle(s_r, h_r, k_r)
    
    # Calculate balance and weight distribution
    shoulder_center = [(s_l[0] + s_r[0])/2, (s_l[1] + s_r[1])/2]
    hip_center = [(h_l[0] + h_r[0])/2, (h_l[1] + h_r[1])/2]
    balance = abs(shoulder_center[0] - hip_center[0])
    
    # Weight distribution (estimated based on foot position)
    weight_distribution = (
        (1 - (f_l[0] + 1)/2) * 100,  # Left leg weight %
        (1 - (f_r[0] + 1)/2) * 100    # Right leg weight %
    )

    # Identify technique
    technique = identify_technique(
        w_r, w_l, h_r, h_l, 
        elbow_r, elbow_l,
        s_r, e_r,
        s_l, e_l,
        knee_r, knee_l
    )
    
    # Detect advanced errors
    errors = detect_errors(
        technique, 
        elbow_l, elbow_r, 
        knee_l, knee_r, 
        balance,
        weight_distribution,
        shoulder_hip_angle_l,
        shoulder_hip_angle_r
    )
    
    # Overall evaluation
    score = evaluate_performance(technique, errors)
    
    return {
        "technique": technique,
        "technique_info": TECHNIQUE_DB.get(technique, {}),
        "errors": errors,
        "performance_score": score,
        "angles": {
            "elbow_left": round(elbow_l, 1),
            "elbow_right": round(elbow_r, 1),
            "knee_left": round(knee_l, 1), 
            "knee_right": round(knee_r, 1),
            "shoulder_hip_left": round(shoulder_hip_angle_l, 1),
            "shoulder_hip_right": round(shoulder_hip_angle_r, 1)
        },
        "balance": round(balance, 3),
        "weight_distribution": {
            "left": round(weight_distribution[0], 1),
            "right": round(weight_distribution[1], 1)
        }
    }

def identify_technique(w_r, w_l, h_r, h_l, elbow_r, elbow_l, s_r, e_r, s_l, e_l, knee_r, knee_l):
    """Identify the current technique being used"""
    # Check for serve
    if (w_r[1] > h_r[1] or w_l[1] > h_l[1]) and elbow_r > 150:
        return "serve"
    
    # Check for forehand
    if elbow_r < 100 and s_r[0] < e_r[0] < w_r[0] and knee_r < 150:
        return "forehand"
    
    # Check for backhand
    if elbow_l < 100 and s_l[0] > e_l[0] > w_l[0] and knee_l < 150:
        return "backhand"
    
    # Check for volley
    if (elbow_r > 60 and elbow_r < 100) or (elbow_l > 60 and elbow_l < 100):
        return "volley"
    
    # Default to ready stance
    return "ready_stance"

def check_swing_mechanics(frames):
    """Check swing mechanics (backswing and follow-through)"""
    if len(frames) < 5:
        return False
    
    # Get key frames
    first_frame = frames[0]
    middle_frame = frames[len(frames)//2]
    last_frame = frames[-1]
    
    # Check backswing (racket moving back)
    has_backswing = (
        first_frame['analysis']['angles']['elbow_right'] < 
        middle_frame['analysis']['angles']['elbow_right']
    )
    
    # Check follow-through (racket moving forward)
    has_followthrough = (
        last_frame['analysis']['angles']['elbow_right'] > 
        middle_frame['analysis']['angles']['elbow_right']
    )
    
    return has_backswing and has_followthrough

def analyze_footwork(frames):
    """Analyze foot movement"""
    if len(frames) < 5:
        return 'static'
    
    # Calculate foot position changes
    first_pos = (
        frames[0]['analysis']['weight_distribution']['left'],
        frames[0]['analysis']['weight_distribution']['right']
    )
    last_pos = (
        frames[-1]['analysis']['weight_distribution']['left'],
        frames[-1]['analysis']['weight_distribution']['right']
    )
    
    # Calculate weight shift
    weight_change = abs(first_pos[0] - last_pos[0]) + abs(first_pos[1] - last_pos[1])
    
    if weight_change > 30:
        return 'good_movement'
    elif weight_change > 15:
        return 'some_movement'
    else:
        return 'static'

def detect_errors(technique, elbow_l, elbow_r, knee_l, knee_r, balance, weight_distribution, shoulder_hip_angle_l, shoulder_hip_angle_r):
    """Detect advanced errors based on technique"""
    errors = []
    tech_data = TECHNIQUE_DB.get(technique, {})
    key_points = tech_data.get("key_points", {})
    
    # Check knee angle
    if "knee_angle" in key_points:
        min_knee, max_knee = key_points["knee_angle"]
        if knee_l > max_knee or knee_r > max_knee:
            errors.append(ERROR_DB["knee_straight"])
    
    # Check elbow angle
    if "elbow_angle" in key_points:
        min_elbow, max_elbow = key_points["elbow_angle"]
        if elbow_l < min_elbow or elbow_r < min_elbow:
            errors.append(ERROR_DB["elbow_straight"])
    
    # Check balance
    if "balance_threshold" in key_points:
        if balance > key_points["balance_threshold"]:
            errors.append(ERROR_DB["poor_balance"])
    
    # Check weight distribution
    if "weight_distribution" in key_points:
        ideal_left, ideal_right = key_points["weight_distribution"]
        current_left, current_right = weight_distribution
        if abs(current_left - ideal_left) > 20 or abs(current_right - ideal_right) > 20:
            errors.append({
                "description": "Incorrect weight distribution",
                "harm": "Reduces mobility and reaction ability",
                "correction": f"Distribute weight {ideal_left}% left leg and {ideal_right}% right leg",
                "severity": "medium"
            })
    
    # Check technique-specific common mistakes
    for mistake in tech_data.get("common_mistakes", []):
        if mistake in ERROR_DB:
            # Add specific checks for each mistake
            if mistake == "serve_wrist_flip" and technique == "serve":
                errors.append(ERROR_DB[mistake])
            elif mistake == "forehand_grip_too_tight" and technique == "forehand":
                errors.append(ERROR_DB[mistake])
            # ... (add checks for other mistakes)
    
    return errors

def evaluate_performance(technique, errors):
    """Evaluate performance based on technique and errors"""
    base_score = 100
    tech_data = TECHNIQUE_DB.get(technique, {})
    
    # Deduct points based on error severity
    for error in errors:
        if error.get('severity') == 'high':
            base_score -= 15
        elif error.get('severity') == 'medium':
            base_score -= 10
        else:
            base_score -= 5
    
    # Ensure score is within bounds
    return max(0, min(100, base_score))

def analyze_dynamic_movements(frames):
    """Analyze movements across multiple frames"""
    if len(frames) < 10:
        return {}  # Not enough frames for analysis
    
    # Analyze movement sequence
    movements = {
        'weight_transfer': check_weight_transfer(frames),
        'backswing_followthrough': check_swing_mechanics(frames),
        'footwork_pattern': analyze_footwork(frames)
    }
    
    return {
        'dynamic_analysis': movements,
        'improvement_suggestions': generate_dynamic_suggestions(movements)
    }

def check_weight_transfer(frames):
    """Check weight transfer during swing"""
    weight_changes = []
    for frame in frames[-10:]:  # Consider last 10 frames
        if 'weight_distribution' in frame['analysis']:
            weight_changes.append(
                frame['analysis']['weight_distribution']['left'] - 
                frame['analysis']['weight_distribution']['right']
            )
    
    if len(weight_changes) < 2:
        return False
        
    # Check for significant weight change
    return abs(weight_changes[-1] - weight_changes[0]) > 20  # At least 20% change

def generate_dynamic_suggestions(movements):
    """Generate improvement suggestions based on dynamic analysis"""
    suggestions = []
    
    if not movements['weight_transfer']:
        suggestions.append({
            'title': 'Weight transfer during swing',
            'description': 'You are not transferring enough weight from back to front foot',
            'drill': 'Practice swing motion without ball, focus on weight transfer'
        })
    
    if not movements['backswing_followthrough']:
        suggestions.append({
            'title': 'Lack of follow-through',
            'description': 'Your swing stops immediately after ball contact',
            'drill': 'Practice swing focusing on completion, racket should finish behind shoulder'
        })
    
    if movements['footwork_pattern'] == 'static':
        suggestions.append({
            'title': 'Insufficient foot movement',
            'description': 'You are standing too still during swing',
            'drill': 'Practice small step movements before swinging'
        })
    
    return suggestions

def analyze_video_frame(frame, previous_frames=[]):
    """Analyze a video frame with context from previous frames"""
    # Convert color
    image = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
    image.flags.writeable = False
    
    # Initialize pose estimation
    with mp_pose.Pose(
        min_detection_confidence=0.7,
        min_tracking_confidence=0.7,
        model_complexity=1
    ) as pose:
        results = pose.process(image)
    
    # Prepare result
    result = {
        'technique': 'unknown',
        'errors': [],
        'angles': {},
        'balance': 0,
        'weight_distribution': {'left': 50, 'right': 50},
        'annotated_frame': frame  # Original frame if no detection
    }
    
    if results.pose_landmarks:
        # Analyze technique
        analysis = analyze_technique(results.pose_landmarks.landmark)
        
        # Draw landmarks on frame
        annotated_image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)
        mp_drawing.draw_landmarks(
            annotated_image, 
            results.pose_landmarks, 
            mp_pose.POSE_CONNECTIONS,
            mp_drawing.DrawingSpec(color=(245,117,66), thickness=2, circle_radius=2),
            mp_drawing.DrawingSpec(color=(245,66,230), thickness=2, circle_radius=2)
        )
        
        # Draw technique guidelines
        annotated_image = draw_technique_guidelines(
            annotated_image, 
            results.pose_landmarks, 
            analysis["technique"]
        )
        
        # Add analysis to result
        result.update(analysis)
        result['annotated_frame'] = annotated_image
    
    # Add dynamic analysis if enough previous frames
    if len(previous_frames) >= 10:
        dynamic_analysis = analyze_dynamic_movements(previous_frames[-10:] + [{
            'analysis': result,
            'frame': frame
        }])
        result.update(dynamic_analysis)
    
    return result

def analyze_video(video_path):
    cap = cv2.VideoCapture(video_path)
    
    # Check FPS and video duration
    fps = cap.get(cv2.CAP_PROP_FPS)
    frame_count = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))
    duration = frame_count / fps if fps > 0 else 0
    
    if duration > 30:  # Limit to 30 second videos
        cap.release()
        return {"error": "Video too long. Please upload a video shorter than 30 seconds."}
    
    results = {
        "incorrect_frames": [],
        "error_summary": defaultdict(int),
        "technique_stats": defaultdict(int),
        "video_info": {
            "duration": duration,
            "fps": fps,
            "frame_count": frame_count
        },
        "performance_over_time": []
    }

    previous_frames = []
    
    with mp_pose.Pose(
        min_detection_confidence=0.7,
        min_tracking_confidence=0.7,
        model_complexity=1
    ) as pose:
        frame_idx = 0
        while cap.isOpened():
            ret, frame = cap.read()
            if not ret:
                break

            # Process every 5th frame for performance
            if frame_idx % 5 != 0:
                frame_idx += 1
                continue

            try:
                # Analyze frame with previous frame context
                result = analyze_video_frame(frame, previous_frames)
                previous_frames.append({
                    'analysis': result,
                    'frame': frame
                })
                
                results["technique_stats"][result["technique"]] += 1
                results["performance_over_time"].append(result["performance_score"])
                
                if result["errors"]:
                    frame_data = {
                        "image": result["annotated_frame"],
                        "time": cap.get(cv2.CAP_PROP_POS_MSEC) / 1000,
                        "analysis": {
                            "technique": result["technique"],
                            "errors": result["errors"],
                            "angles": result["angles"],
                            "balance": result["balance"],
                            "weight_distribution": result["weight_distribution"]
                        }
                    }
                    results["incorrect_frames"].append(frame_data)
                    
                    for error in result["errors"]:
                        results["error_summary"][error["description"]] += 1
                        
            except Exception as e:
                print(f"Error processing frame {frame_idx}: {str(e)}")
            
            frame_idx += 1

    cap.release()
    
    if not results["technique_stats"]:
        return {"error": "No poses detected. Please check your video."}
    
    # Calculate average score
    if results["performance_over_time"]:
        results["average_performance"] = sum(results["performance_over_time"]) / len(results["performance_over_time"])
    else:
        results["average_performance"] = 0
    
    results["error_summary"] = dict(results["error_summary"])
    results["technique_stats"] = dict(results["technique_stats"])
    
    return results