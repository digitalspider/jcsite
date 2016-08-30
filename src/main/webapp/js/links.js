$(document).ready(function () {
	var formURL = "/website/public/link";
	$.ajax({
			url: formURL,
			type: "POST",
			dataType: 'json',
			success: function (data, textStatus, jqXHR) {
				var $el = $("#links");
				var $backup = $.extend( true, {}, $el );
				$el.empty(); // remove old values
				$.each(data, function(key, value) {
					$el.append($("<li></li>").append($("<a></a>").attr("href", value).attr("class","tm-text-link").text(key)));
				});
			},
			error: function (jqXHR, textStatus, errorThrown) {
				$el = $backup;
			}
	});
});