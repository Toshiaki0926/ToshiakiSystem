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
1. クラス定義やメソッド定義（例: classやpublic static void mainなど）は無視してください。
2. 行番号は無視した行も含めて全体の行番号を維持してください。
3. 各コード行について、その行単体での動作や役割を説明してください。

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
    "line_number": 5,
    "code": "System.out.println("Hello");",
    "description": "「Hello」と画面出力する"
  }}
]

Javaコード:
{file_content}
"""

# Gemini APIを呼び出してレスポンスを取得
# Change: Pass the prompt as the first positional argument instead of using 'content='
response = model.generate_content(prompt)  
response_text = response.text

try:
    # 前後の囲み記号を正規表現で除去
    cleaned_response = re.sub(r"```json|```", "", response_text).strip()
    
    # クリーニングしたレスポンスをJSONとしてパース
    json_data = json.loads(cleaned_response)

    # JSONデータをファイルに保存
    with open(r"C:\pleiades\2023-12\workspace\Hint1\json\output.json", "w", encoding="utf-8") as json_file:
        json.dump(json_data, json_file, ensure_ascii=False, indent=4)

    print("JSONデータをoutput.jsonに保存しました。")
except json.JSONDecodeError:
    print("GeminiからのレスポンスをJSONとしてパースできませんでした:")
    print(response_text)