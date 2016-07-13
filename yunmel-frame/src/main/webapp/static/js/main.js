var $curmenu,lastIndex;
var webHistory = Webit.history;
jQuery(function($) {
	initMenuClick();
});

function initMenuClick(){
	var menus = $("#sidebar .sidebar-menu a[id]");
	menus.on("click",function(){
		var hash = webHistory.get(),href = $(this).attr("href");
		if(("#"+hash) == href ){
			webHistory.justShow("#");
			webHistory.go(hash);
		}
		var $main_content = $("#content-wrapper");
		webHistory.add("ajax", function(str, action, token) {
			$main_content.html("");
			
			$main_content.html(loadHtmlPage(str));
			
			var curMenu = $("#sidebar .nav li").find("a[href='#"+token+"']");
			$("title").html(yunmel.vars['title'] + '-' +curMenu.find('.menu-text').text())
			changeMenu(curMenu);
		});
		webHistory.init();
		$(document).tooltip({
	        selector: "[data-toggle=tooltip]",
	        container: "body"
	    });
	});
}

function loadHtmlPage(path,data){
	var result;
	$.ajax({
		url: path,
		method:'post',
		data:data,
		dataType: "text",
		async: false,
		success: function(html) {
			result = html;
		}
	});
	return result;
}