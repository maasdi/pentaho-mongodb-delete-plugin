$(document).ready(function(){
	createNav();

	$('#responsive-menu-button').sidr({
      name: 'sidr-main',
      source: '#navigation'
    });
});

function createNav() {
	var nav = '<div id="mobile-header"><a id="responsive-menu-button" href="#sidr-main">Menu</a></div>';
	nav += '<div id="navigation">';
	nav += '<nav class="nav">';
	nav += '<ul class="nav-bar">';
	nav += '<li><a href="#getstarted">Get started</a></li>';
	nav += '<li><a href="#documentation">Documentation</a></li>';
	nav += '<li><a href="#support">Support</a></li>';
	nav += '</ul>';
	nav += '</nav>';
	nav += '</div>';
	
	$('body').append(nav);
}