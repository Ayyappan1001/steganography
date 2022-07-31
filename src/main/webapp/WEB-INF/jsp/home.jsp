<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>steganography</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    

<div class="split left">
  <div class="centered">
  <div class="form">
    <h3>Encode</h3>
             <form action="encode" method="post"  enctype="multipart/form-data">
                <input type="file" name="image" class="input-box" placeholder="Select File">
                <input type="text" name="message" class="input-box" placeholder="message">
                <input type="text" name="key" class="input-box" placeholder="key">
                <input type="submit" name="login-btn" class="btn" value="Encode">
                <h5><span class="message">${encodeMessage}</span></h5>
                <h5><span class="message">${encodeErrorMessage}</span></h5>
            </form>
  </div>
  </div>
</div>

<div class="split right">
  <div class="centered">
  <div class="form">
     <h3>Decode</h3>
            <form action="decode" method="post"  enctype="multipart/form-data">
                <input type="file" name="image" class="input-box" placeholder="Select File">
                <input type="text" name="key" class="input-box" placeholder="key">
                <input type="submit" name="signup-btn" class="btn" value="Decode">
				<p><span class="message"> ${decodeMessage}</span></p>
                <h5><span class="message">${decodeErrorMessage}</span></h5>
            </form>
	</div>
  </div>
</div>
     

</body>
</html>