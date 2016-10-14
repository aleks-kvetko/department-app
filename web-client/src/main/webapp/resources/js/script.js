function hide() {
	
	if(document.getElementById("certainRadio").checked) {
	document.getElementById('startDate').style.display="none";
	document.getElementById('endDate').style.display="none";
	document.getElementById('startDate').value="";
	document.getElementById('endDate').value="";
	document.getElementById('startLable').style.display="none";
	document.getElementById('endLable').style.display="none";
	
	
	document.getElementById('certainDate').style.display="inline";
	document.getElementById('certainLable').style.display="inline";
	
	

	}
		if(document.getElementById("intervalRadio").checked) {
	document.getElementById('certainLable').style.display="none";
	document.getElementById('certainDate').style.display="none";
	document.getElementById('certainDate').value="";
	document.getElementById('startDate').style.display="inline";
	document.getElementById('endDate').style.display="inline";
	document.getElementById('startLable').style.display="inline";
	document.getElementById('endLable').style.display="inline";
	
	} }