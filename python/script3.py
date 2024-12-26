import google.generativeai as genai
import json
import sys
import io;
import re  # 正規表現モジュールをインポート
sys.stdout.reconfigure(encoding='utf-8')  # 標準出力をUTF-8に設定
sys.stdin = io.TextIOWrapper(sys.stdin.buffer, encoding='utf-8')

# Gemini APIの設定
genai.configure(api_key='AIzaSyDHu8YpeNVyGW8BMx0_Z1cV8f8HsQpxKlk')
model = genai.GenerativeModel("gemini-1.5-flash")

# 文字列を明示的にUTF-8でデコード
file_content = sys.stdin.read().strip()
#file_content = file_content.encode('utf-8', 'ignore').decode('utf-8', 'ignore')


# Geminiに送るプロンプトを準備
prompt = f"""
以下のJavaコードを1行ずつに分解し、それぞれに日本語で簡潔な説明を付けてJSON形式で出力してください。
注意点:
1. インポートやクラス定義などは無視せず、すべての行について説明を付けてください。ただし、空行は無視してください。
2. 各コード行について、その行単体での動作や役割を説明してください。
3. json形式の前後に「```json」と「```」を含まないでください。

例:
Javaコード: 
int[] score = new int[6];
for(int i = 0; i < len; i++) {{
  total += score[i];
}}
System.out.println(total);
System.out.println("Hello");

出力例（JSON形式）:
[
  {{
    "line_number": 1,
    "code": "int[] score = new int[6];",
    "description": "要素数6の整数型配列scoreを宣言する"
  }},
  {{
    "line_number": 2,
    "code": "for(int i = 0; i < len; i++) ",
    "description": "iを0からlen未満まで繰り返す"
  }},
  {{
    "line_number": 3,
    "code": "total += score[i];",
    "description": "totalにscore配列のi番目の要素を加算する"
  }},
  {{
    "line_number": 4,
    "code": "System.out.println(total);",
    "description": "totalの値を画面出力する"
  }},
  {{
    "line_number": ,
    "code": "System.out.println("Hello");",
    "description": "「Hello」と画面出力する"
  }}
]
以下が分解して欲しいJavaコードです:
{file_content}
"""

# Gemini APIを呼び出してレスポンスを取得
# Change: Pass the prompt as the first positional argument instead of using 'content='
response = model.generate_content(prompt)  
response_text = response.text

# 不正な制御文字を取り除くための正規表現
cleaned_response = re.sub(r'[\x00-\x1F\x7F]', '', response_text)

# その後、パースを試みる　「[」と「]」の間だけをパース
json_match = re.search(r"\[.*\]", cleaned_response, re.DOTALL)

if json_match:
    json_text = json_match.group(0).strip()  # マッチ部分をトリムして取得
        
    try:
        json_data = json.loads(json_text)  # 抽出部分をJSONとしてパース

        # JSONデータをファイルに保存
        with open(r"C:\pleiades\2023-12\workspace\Hint1\json\output.json", "w", encoding="utf-8") as json_file:
            json.dump(json_data, json_file, ensure_ascii=False, indent=4)

        print("JSONデータをoutput.jsonに保存しました。")
    except json.JSONDecodeError as e:
        print("GeminiからのレスポンスをJSONとしてパースできませんでした:")
        print("エラー内容:", e)
        print("レスポンス:", response_text)
else:
    print("JSON部分の抽出に失敗しました。レスポンスの内容を確認してください。")