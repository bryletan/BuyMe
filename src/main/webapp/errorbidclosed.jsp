<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Bid Error</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            flex-direction: column;
            font-family: Arial, sans-serif;
        }
        .message {
            text-align: center;
            margin-bottom: 20px;
        }
        .back-button {
            padding: 10px 20px;
            font-size: 16px;
            text-decoration: none;
            color: white;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
        }
        .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="message">
        <h1>Your bid has not been placed.</h1>
        <h2>This auction is closed.</h2>
    </div>
    <a href="fetchitem" class="back-button">Back to Home</a>
</body>
</html>