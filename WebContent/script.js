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
    	return true;
    }
}

function paintValidate() {
	prodVal = prodValidate("newpaintform");
	if (!prodVal){
		return false;
	}
	var canvasType = document.forms["newpaintform"]["canvasType"];
	var paintType = document.forms["newpaintform"]["paintType"];
	var length = document.forms["newpaintform"]["length"];
	var width = document.forms["newpaintform"]["width"];
		
    if (canvasType.value == "") {
        alert("CanvasType must be filled out");
        canvasType.focus();
        return false;
    }else if (paintType.value == "") {
        alert("PaintType must be filled out");
        paintType.focus();
        return false;
    }else if (length.value == "") {
        alert("Length must be filled out");
        length.focus();
        return false;
    }else if (width.value == "") {
        alert("Width must be filled out");
        width.focus();
        return false;
    }else if (canvasType.value.length > 255) {
        alert("Canvas Type too long!");
        canvasType.focus();
        return false;
    }else if (paintType.value.length > 255) {
        alert("Paint Type too long!");
        paintType.focus();
        return false;
    }else{
    	return true;
    }
}

function sculptValidate() {
	prodVal = prodValidate("newsculptform");
	if (!prodVal){
		return false;
	}
	var material = document.forms["newsculptform"]["material"];
	var length = document.forms["newsculptform"]["length"];
	var width = document.forms["newsculptform"]["width"];
	var height = document.forms["newsculptform"]["height"];
	var weight = document.forms["newsculptform"]["weight"];
		
    if (material.value == "") {
        alert("Material must be filled out");
        material.focus();
        return false;
    }else if (length.value == "") {
        alert("Length must be filled out");
        length.focus();
        return false;
    }else if (width.value == "") {
        alert("Width must be filled out");
        width.focus();
        return false;
    }else if (height.value == "") {
        alert("Height must be filled out");
        height.focus();
        return false;
    }else if (weight.value == "") {
        alert("Weight must be filled out");
        weight.focus();
        return false;
    }else if (material.value.length > 255) {
        alert("Material too long!");
        material.focus();
        return false;
    }else{
    	return true;
    }
}

function craftValidate() {
	prodVal = prodValidate("newcraftform");
	if (!prodVal){
		return false;
	}
	var usage = document.forms["newcraftform"]["usage"];
	var length = document.forms["newcraftform"]["length"];
	var width = document.forms["newcraftform"]["width"];
	var height = document.forms["newcraftform"]["height"];
		
    if (usage.value == "") {
        alert("Usage must be filled out");
        usage.focus();
        return false;
    }else if (length.value == "") {
        alert("Length must be filled out");
        length.focus();
        return false;
    }else if (width.value == "") {
        alert("Width must be filled out");
        width.focus();
        return false;
    }else if (height.value == "") {
        alert("Height must be filled out");
        height.focus();
        return false;
    }else if (usage.value.length > 255) {
        alert("Usage too long!");
        usage.focus();
        return false;
    }else{
    	return true;
    }
}