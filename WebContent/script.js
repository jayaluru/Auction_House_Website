function loginValidate(){
	var username = document.forms["loginform"]["username"].value;
	var password = document.forms["loginform"]["password"].value;
	
	if (username == "") {
        alert("username must be filled out");
        document.forms["loginform"]["username"].focus();
        return false;
    }else if (password== "") {
        alert("password must be filled out");
        document.forms["loginform"]["password"].focus();
        return false;
    }else if (rpassword == "") {
        alert("retry-password must be filled out");
        document.forms["loginform"]["retry-password"].focus();
        return false;
    }else if(password != rpassword){
    	alert("password doesnt match");
        document.forms["loginform"]["password"].focus();
        return false;
    }else{
    	return true;
    }
}

function regValidate() {

	var username = document.forms["regform"]["username"].value;
	var password = document.forms["regform"]["password"].value;
	var rpassword = document.forms["regform"]["retry-password"].value;
	var expdate = document.forms["regform"]["expdate"].value;
	expdate = Date.parse(expdate);
	var cvv = document.forms["regform"]["cvv"].value;
	var number = document.forms["regform"]["number"].value;
	var name = document.forms["regform"]["name"].value;
	var description = document.forms["regform"]["description"].value;
	var address = document.forms["regform"]["address"].value;
	if (username == "") {
        alert("username must be filled out");
        document.forms["regform"]["username"].focus();
        return false;
    }else if (password== "") {
        alert("password must be filled out");
        document.forms["regform"]["password"].focus();
        return false;
    }else if (rpassword == "") {
        alert("retry-password must be filled out");
        document.forms["regform"]["retry-password"].focus();
        return false;
    }else if(password != rpassword){
    	alert("password doesnt match");
        document.forms["regform"]["password"].focus();
        return false;
    }else if(isNaN(expdate)){
    	alert("expiration date invalid");
        document.forms["regform"]["expdate"].focus();
        return false;
    }else if(cvv != null && cvv.length > 4){
    	alert("cvv invalid");
        document.forms["regform"]["cvv"].focus();
        return false;
    }else if(number != null && number.length > 19){
    	alert("credit card number invalid");
        document.forms["regform"]["number"].focus();
        return false;
    }else if(username != null && username.length > 16){
    	alert("username too long!");
        document.forms["regform"]["username"].focus();
        return false;
    }else if(password != null && password.length > 32){
    	alert("password too long!");
        document.forms["regform"]["password"].focus();
        return false;
    }else if(name != null && name.length > 100){
    	alert("name too long!");
        document.forms["regform"]["name"].focus();
        return false;
    }else if(address != null && address.length > 255){
    	alert("address too long!");
        document.forms["regform"]["address"].focus();
        return false;
    }else if(description != null && description.length > 2000){
    	alert("description too long!");
        document.forms["regform"]["description"].focus();
        return false;
    }else{
    	return true;
    }
}

function prodValidate(formname) {
	var name = document.forms[formname]["name"];
	var description = document.forms[formname]["description"];
	var price = document.forms[formname]["price"];
	var biddate = document.forms[formname]["biddate"];

	if (name.value == "") {
        alert("Name must be filled out");
        name.focus();
        return false;
    }else if (description.value == "") {
        alert("Description must be filled out");
        description.focus();
        return false;
    }else if (price.value == "") {
        alert("Price must be filled out");
        price.focus();
        return false;
    }else if (name.value.length > 255) {
        alert("Name too long!");
        name.focus();
        return false;
    }else if (description.value.length > 2000) {
        alert("Description too long!");
        description.focus();
        return false;
    }else{
    	var today = new Date().getTime(),
    	biddate = biddate.split("-");

	    idate = new Date(biddate[0], biddate[1] - 1, biddate[2]).getTime();
	    if((today - idate) >= 0){
	    	alert("Please enter future date!");
	    	biddate.focus();
	        return false;
	    }
    }
}
