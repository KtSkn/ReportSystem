/**
 * 
 */

'use strict'

console.log("Called");

//Jquery
//unrecognized expression: a[href^=#]
/////from//////////////////////////////
//  $('a[href^=#]').click(function () {
//  });
////////to//////////////////////////////
//  $('a[href^=\\#]').click(function () {
//  });
////////////////////////////////////////
//const nodelist = document.querySelectorAll('a[href^=\\#]');
//const len = nodelist.length;
//
//
//
////フォーカスアウト
//const execute = document.getElementById('execute');
//
//execute.addEventListener('focusout', (event) => {
//	console.log(execute);
//	if (execute.value == "") {
//		alert("値を入力してください");
//	}
//	event.target.style.background = 'blue';
//});
//
//エラーダイヤログ表示
document.addEventListener('DOMContentLoaded', function() {

	const $errormessage = $('#errormessage').get(0);

	if (!($errormessage === undefined) && $errormessage.textContent !== "") {
		alert($errormessage.textContent);

	}
	let bindingErrorMessage = document.getElementsByClassName('valueerror')[0];

	//	if(location.href == "http://localhost:8080/execute"){
	//		if (typeof bindingErrorMessage === "undefined") {
	//		console.log('未定義');
	//		alert("帳票を出力しますか");
	//		
	//		let newwin = open('http://localhost:8080/jasper');
	//		
	//		location.href = 'http://localhost:8080/index';
	//	}	

	//	}
	//	if (typeof bindingErrorMessage === "undefined") {
	//		console.log('未定義');
	//
	//	} else {
	//		console.log(bindingErrorMessage.textContent);
	//		let message = bindingErrorMessage.textContent;
	//		alert(message);
	//
	//	}


});


document.addEventListener('keydown',(e) => {
	document.querySelector('p').textContent = e.key;
	
});


document.querySelector('input').addEventListener('focus', () => {
	console.log('clicked');
	document.querySelector('p').textContent = 'aaaa';
});

document.querySelector('input').addEventListener('blur', () => {
	console.log('clicked');
	document.querySelector('p').textContent = '';
});

$('#abutton').click(function() {
	console.log('クリックされました！');
});


//const execute = $('#execute');
$('#execute').click(function() {

//	document.querySelector('input')
//	document.querySelectorAll('input').forEach((checkbox) => { 
//		
//		console.log(checkbox.checked); 
//	});


	const myform2 = $('#myform2');

	console.log('クリックされました！');
	console.log(myform2);
	const $wariate = $('#myform2 [name=wariate]');
	const $checked = $('#myform2 [name=checked]').prop('checked');
	const $text = $('#myform2 [name=text]');
	//    const $wariate = myform2[name=wariate];
	console.log($wariate.val());
	console.log($checked);
	console.log($text.val());

	myform2.submit();

});


















