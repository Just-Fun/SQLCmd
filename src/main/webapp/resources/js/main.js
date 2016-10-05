function init(ctx) {

 var fromPage = null;

    var showFromPage = function() {
        window.location.hash = fromPage;
        fromPage = null;
    }

    var hideAllScreens = function() {
        $('#tables').hide();
        $('#databases').hide();
        $('#menu').hide();
        $('#table').hide();
        $('#actions').hide();
        $('#connecting-form').hide();
    }

    var loadPage = function(data) {
        hideAllScreens();
        $("#loading").show();

        var page = data[0];
        if (page == 'tables') {
            initTables();
        } else if (page == 'databases') {
            initDatabases(data[1]);
        } else if (page == 'table') {
            initTable(data[1]);
        } else if (page == 'menu') {
            initMenu();
        } else if (page == 'actions') {
            initActions();
        } else if (page == 'connect') {
            initConnect();
        } else {
            window.location.hash = "/menu";
        }
    }

    var isConnected = function(url, onConnected) {
            $.get(ctx + "/connected", function(userName) {
                if (userName == "") {
                    fromPage = url;
                    window.location.hash = '/connect';
                } else {
                    if (!!onConnected) {
                        onConnected(userName);
                    }
                }
            });
        }

    var gotoConnectPage = function(fromPage) {
        window.location = ctx + '/connect' + '?fromPage=' + escape('/main#/' + fromPage);
    }

    var show = function(selector) {
        var component = $(selector);
        component.find('.container').children().not(':first').remove();
        component.show();
    }

     var initConnect = function() {
        $("#database").val("");
        $("#username").val("");
        $("#password").val("");
        $('#error').hide();
        $('#error').html("");
        $("#loading").hide(300, function() {
            $('#connecting-form').show();
        });
    };

    var initTables = function() {
        isConnected("tables", function() {
            show('#tables');

            $.get(ctx + "/tables/content", function(elements) {
                $("#loading").hide(300, function() {
                    $('#tables script').tmpl(elements).appendTo('#tables .container');
                });
            });
        });
    };

     var initDatabases = function() {
        isConnected("databases", function() {
            show('#databases');

            $.get(ctx + "/databases/content", function(elements) {
                $("#loading").hide(300, function() {
                    $('#databases script').tmpl(elements).appendTo('#databases .container');
                });
            });
        });
    };

     var initActions = function() {
            isConnected("actions", function() {
                show('#actions');

                $.get(ctx + "/actions/content", function(elements) {
                    $("#loading").hide(300, function() {
// TODO remove all in container before append
//                        $('#actions container').empty();
//                        $('#actions #actionsScript').tmpl(elements).appendTo('#actions .container');
                        $('#actions script').tmpl(elements).appendTo('#actions .container');

                    });
                });
            });
        };

    var initTable = function(tableName) {
        isConnected("table/" + tableName, function() {
            show('#table');

            $.get(ctx + '/table/' + tableName + '/content', function(elements) {
                $('#loading').hide(300, function() {
                    $('#table script').tmpl(elements).appendTo('#table .container');
                });
            });
        });
    };

    var initMenu = function() {
        show('#menu');

        $.get(ctx + "/menu/content", function(elements) {
            $("#loading").hide(300, function() {
                $('#menu script').tmpl(elements).appendTo('#menu .container');
            });
        });
    };

    var load = function() {
        var hash = window.location.hash.substring(1);
        var parts = hash.split('/');
        if (parts[0] == '') {
            parts.shift();
        }
        loadPage(parts);
    }

    $('#connect').click(function() {
        var connection = {};
        connection.database = $("#database").val();
        connection.userName = $("#username").val();
        connection.password = $("#password").val();

        $.ajax({
            url: ctx + "/connect",
            data: connection,
            type: 'POST',
            success: function(message) {
                if (message == "" || message == null) {
                    showFromPage();
                } else {
                    $('#error').html(message);
                    $('#error').show();
                }
            }
        });
    });

    $(window).bind('hashchange', function(event) {
        load();
    });

    load();
}
