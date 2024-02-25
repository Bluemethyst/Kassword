from flask import Flask, request, render_template, send_file
import requests
import pandas as pd

app = Flask(__name__)


@app.route("/download_passwords", methods=["GET"])
def download_passwords():
    return send_file("passwords.csv", as_attachment=True)


@app.route("/", methods=["GET"])
def generate_password():
    params = request.args.to_dict()
    checkbox_fields = [
        "animals",
        "instruments",
        "colours",
        "shapes",
        "food",
        "sports",
        "transport",
        "symbols",
        "capitals",
        "randCapitals",
    ]
    for field in checkbox_fields:
        if field in params:
            params[field] = "true"
        else:
            params[field] = "false"
    url = "https://password.ninja/api/password?"
    response = requests.get(url, params=params)
    pwd_list = response.json()
    try:
        # Convert the list to a DataFrame and save it as a CSV file
        df = pd.DataFrame(pwd_list, columns=["Passwords"])
        df.to_csv("passwords.csv", index=False)
    except ValueError:
        pass
    pwd_str = ""
    for pwd in response.json():
        pwd_str += f"{pwd}\n"
    return render_template("index.html", response=pwd_str)


if __name__ == "__main__":
    app.run(debug=True)
