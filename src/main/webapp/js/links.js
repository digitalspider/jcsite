$(document).ready(function () {
	var formURL = "/website/public/link";
	$.ajax({
			url: formURL,
			type: "POST",
			dataType: 'json',
			success: function (data, textStatus, jqXHR) {
				var ele = $("#links");
				var backup = $.extend( true, {}, ele );
				ele.empty(); // remove old values
				$.each(data, function(key, value) {
					ele.append($("<li></li>")
						.append($("<a></a>").attr("href", value).attr("class","tm-text-link").text(key)));
				});
			},
			error: function (jqXHR, textStatus, errorThrown) {
				ele = backup;
			}
	});
});