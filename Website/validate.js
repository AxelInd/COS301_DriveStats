function validate()
{
	validateName();
	validateSurName();
	validateEmail();
	validatePassword();
}

function validateName()
{
    var temp = document.forms["myForm"]["name"].value;
	
	
	if(!isNaN(parseFloat(temp)) && isFinite(temp))
	{
		alert("Name may contain no numbers");
		return false;
	}
	else if (temp == null || temp == "") 
	{
        alert("Name must be filled out");
        return false;
    }
	else	
		return true;
}

function validateSurName()
{
    var temp = document.forms["myForm"]["sur"].value;
    
	if (temp == null || temp == "") 
	{
        alert("Surname must be filled out");
        return false;
    }
	if(!isNaN(parseFloat(temp)) && isFinite(temp))
	{
		alert("Surname may contain no numbers");
		return false;
	}
	else
		return true;
}

function validateEmail()
{
    var temp = document.forms["myForm"]["email"].value;
	
	var check = temp.search(/.+@.+/);
	
	if(check == 0)
	return true;
	else
	{
		alert("Error: Invalid e-mail address.");
		return false;
	}
    
	if (temp == null || temp == "") 
	{
        alert("Email must be filled out");
        return false;
    }
	else
		return true;
}

function validatePassword()
{
    var temp = document.forms["myForm"]["pass"].value;
    
	if (temp == null || temp == "") 
	{
        alert("Password must be filled out");
        return false;
    }
	else return true;
}
