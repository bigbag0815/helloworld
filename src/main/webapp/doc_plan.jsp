<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<body>
	<h1>File Upload with Jersey</h1>
 
	<form action="rest/v1/doc/plan/uploads" method="post" enctype="multipart/form-data">
	doc_id :<input type="text"  name="doc_id" placeholder="please input user name"  required="required"/><br>
    description: <input type="text"  name="description" >
	   <p>
		Select a file : <input type="file" name="file" size="45" /><br>
	   </p>
	   <input type="submit" value="Upload It" />
	</form>
 
</body>
</html>

