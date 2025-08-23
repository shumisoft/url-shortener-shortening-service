package com.shumisoft.url_shortener_shortening_service.page;

public class HomePage {
  public static final String html = """
      <!DOCTYPE html>
      <html lang="en">
      <head>
        <meta charset="UTF-8">
        <title>URL Shortener</title>
        <style>
          body {
            font-family: Arial, sans-serif;
            background: #f9fafb;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
          }
          .container {
            background: white;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            text-align: center;
            width: 320px;
          }
          h1 {
            font-size: 1.4rem;
            margin-bottom: 1rem;
            color: #111827;
          }
          input[type="text"] {
            width: 100%;
            padding: 0.7rem;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            margin-bottom: 1rem;
            font-size: 0.95rem;
            box-sizing: border-box;
          }
          button {
            background: #2563eb;
            color: white;
            padding: 0.7rem 1.2rem;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 0.95rem;
            transition: background 0.2s ease;
          }
          button:hover {
            background: #1e40af;
          }
          .result {
            margin-top: 1rem;
            font-size: 0.95rem;
            color: #065f46;
            word-break: break-all;
          }
        </style>
      </head>
      <body>
        <div class="container">
          <h1>Shorten Your URL</h1>
          <form id="shorten-form">
            <input type="text" id="url-input" name="url" placeholder="Enter your long URL" required>
            <button type="submit">Shorten</button>
          </form>
          <div class="result" id="result"></div>
        </div>

        <script>
          document.getElementById('shorten-form').addEventListener('submit', async function(event) {
            event.preventDefault(); // stop normal form submission
            const urlInput = document.getElementById('url-input').value;
            const resultDiv = document.getElementById('result');

            try {
              const response = await fetch('/?url=' + encodeURIComponent(urlInput), {
                method: 'POST'
              });

              if (!response.ok) {
                throw new Error('Server error: ' + response.status);
              }

              const shortId = await response.text(); // backend returns base62 string
              const shortUrl = window.location.origin + '/' + shortId;
              resultDiv.innerHTML = 'Shortened URL: <a href="' + shortUrl + '" target="_blank">' + shortUrl + '</a>';
            } catch (err) {
              resultDiv.textContent = 'Error: ' + err.message;
              resultDiv.style.color = 'red';
            }
          });
        </script>
        <script>
          (function() {
            var map = {
              "url-shortener.ritwikrajsingh.com": "https://ritwikrajsingh.com",
              "url-shortener.dipanshushukla.com":  "https://dipanshushukla.com",
              "localhost":                          "http://localhost:3000"
            };

            var host = map[window.location.hostname];
            if (!host) return;

            var s = document.createElement("script");
            s.src = host + "/badge.js";
            s.setAttribute("data-host",     host);
            s.setAttribute("data-position", "60");
            s.setAttribute("data-type",     "regular");
            s.setAttribute("data-theme",    "light");
            document.body.appendChild(s);
          })();
        </script>
      </body>
      </html>
      """;
}
