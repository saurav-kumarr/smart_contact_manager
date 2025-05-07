console.log("Script loaded");



let currentTheme = getTheme();
//initial -->
document.addEventListener("DOMContentLoaded",() =>{
	changeTheme();
});

//TODO
function changeTheme(){
	//set to web page
	changePageTheme(currentTheme,currentTheme);
	
	//set the listner to change theme button
	const changeThemeButton = document.querySelector("#theme_change_button");
	
	//Change text according to the theme
	
	changeThemeButton.addEventListener("click",() =>{
		let oldTheme = currentTheme;
		console.log("change theme button clicked");
		if(currentTheme === "dark"){
			//theme to light
			currentTheme = "light";
		}else{
			//theme to dark
			currentTheme = "dark";
		}
		
		changePageTheme(currentTheme,oldTheme);
		
	});
}
 //set theme to localstorage
function setTheme(theme){
	localStorage.setItem("theme",theme);
}

//get theme from localStorage
function getTheme(){
	let theme = localStorage.getItem("theme");
	return theme ? theme : "light";
}

//change current page theme
function changePageTheme(theme,oldTheme){
	//localStorage main update karenge
	setTheme(currentTheme);
	//remove the current theme
	document.querySelector("html").classList.remove(oldTheme);
	//set the current theme
	document.querySelector("html").classList.add(theme);
	
	//change the text of button
	document
	.querySelector("#theme_change_button")
	.querySelector("span").textContent = theme === "light" ? "Dark":"Light";
}