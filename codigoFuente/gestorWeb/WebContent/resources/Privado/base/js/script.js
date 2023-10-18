/*$(window).bind('beforeunload', function() {
        PF('dialogLayout').show(); 
});
*/
$(function() {
    /*GENERALES*/
    /*Carga el menu en el sidebar*/
    $('#side-menu').metisMenu();
    /*Elimina efecto drag en todos los componentes de la pagina*/
    $("body").attr("ondragstart", "return false;");
    $("body").attr("ondrop", "return false;");

    $('body').on('blur', "input[type='text']", function() {
        var valorInput = '';
        valorInput = $(this).val();
        var valorConTrim = '';
        if (!$(this).val().replace(/\s/g, '').length) {
            $(this).val('');
        } else {
            valorConTrim = $.trim(valorInput);
            valorConTrim = valorConTrim.replace(/\s{2,}/g, ' ');
            $(this).val(valorConTrim);
        }
    });

    $('body').on('blur', 'textarea', function() {
        var valorInput = '';
        valorInput = $(this).val();
        var valorConTrim = '';
        if (!$(this).val().replace(/\s/g, '').length) {
            $(this).val('');
        } else {
            valorConTrim = $.trim(valorInput);
            valorConTrim = valorConTrim.replace(/\s{2,}/g, ' ');
            $(this).val(valorConTrim);
        }
    });



    ////////////////////////////////////////////////////////////////////////////////////////////////

    /*ADMINISTRACION USUARIOS*/

    //Valida que las dos contraseñas sean iguales
    $("[id$='txtConfirmarContrasenia']").next().after('<div id="passNoCoincide" style="display:none" class="ui-message ui-message-error ui-widget ui-corner-all"><span class="ui-message-error-icon"></span><span class="ui-message-error-detail">Las contraseñas no coinciden.</span></div>');

    validaPassword = function() {
        var pass = $("[id$='txtContrasenia']").val();
        var pass2 = $("[id$='txtConfirmarContrasenia']").val();

        if (pass && pass2) {
            if (pass != pass2) {
                $("#passNoCoincide").css('display', 'block');
            } else {
                $("#passNoCoincide").css('display', 'none');
            }
        } else {
            $("#passNoCoincide").css('display', 'none');
        }

    }

    $('body').on('blur', "[id$='txtContrasenia'],[id$='txtConfirmarContrasenia']", function() {
        validaPassword();
    }).on('focus', "[id$='txtContrasenia'],[id$='txtConfirmarContrasenia']", function() {
        validaPassword();
    });

    //Valida el campo de email
    $("[id$='txtCorreoElectronico']").next().after('<div id="emailNoValido" style="display:none" class="ui-message ui-message-error ui-widget ui-corner-all"><span class="ui-message-error-icon"></span><span class="ui-message-error-detail">Email no válido.</span></div>');
    validaEmail = function() {
        var email = $("[id$='txtCorreoElectronico']").val();
        var patron = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (email) {
            if (!patron.test(email)) {
                $("#emailNoValido").css('display', 'block');
            } else {
                $("#emailNoValido").css('display', 'none');
            }
        } else {
            $("#emailNoValido").css('display', 'none');
        }
        $("[id$='txtCorreoElectronico']").val($.trim($("[id$='txtCorreoElectronico']").val()));
    }

    $("body").on('blur', "[id$='txtCorreoElectronico']", function() {
        validaEmail();
    }).on('focus', "[id$='txtCorreoElectronico']", function() {
        validaEmail();
    });



    /*VALIDACION CARGA MASIVA. Activar el boton de Guardar hasta que el nuevo
    lote tenga un nombre*/

    desactivaBtnGuardarLote = function() {

        if ($("input[id$='lote']").length) {
            //var comentarios = $("textarea[id$='comentarios']").val();
            //if(comentarios){
            // PF('wbtnComentarios').enable();
            //}else{
            PF('btnGuardarLote').disable();
            //}
        }
    }
    desactivaBtnGuardarLote();

    $('body').on('focus', "input[id$='lote']", function() {
        var nombre = $(this).val();
        if (nombre) {
            PF('btnGuardarLote').enable();
        } else {
            PF('btnGuardarLote').disable();
        }
    });


    $('body').on('blur', "input[id$='lote']", function() {
        var nombre = $(this).val();
        if (nombre) {
            PF('btnGuardarLote').enable();

        } else {

            PF('btnGuardarLote').disable();
        }
    });


    $('body').on('keyup', "input[id$='lote']", function() {
        var nombre = $(this).val();
        if (nombre) {
            PF('btnGuardarLote').enable();


        } else {

            PF('btnGuardarLote').disable();
        }
    });


    /*Scroll que te lleva a cada componente validado en Usuario Interno y Externo*/

    scrollAelemento = function() {
        if ($("button[id$='btnGuardarUsuario']").length) {
            if ($("span:contains('El dato es requerido.')").length) {
                $('html,body').animate({ scrollTop: $("span:contains('El dato es requerido.')").offset().top - 100 });
            }

        }
    }

    scrollAelemento();

    /*Permite ver el nombre en el input al elegir un archivo en 'Carga Masiva' */
    $('span.ui-fileupload-filename').on('DOMSubtreeModified', function() {

        var textoInput = '';
        var textoInput = $('span.ui-fileupload-filename').html();
        if (textoInput != "") {
            $(".ui-inputfield.ui-inputtext.ui-widget.ui-state-default.ui-corner-all.ui-state-disabled.form-control").val(textoInput);
        }
        $('span.ui-fileupload-filename').html("");

    });

    /*Reemplaza el icono del boton en imagen de perfil*/
    $('.imgPerfil .ui-button-icon-left.ui-icon.ui-c.ui-icon-plusthick').removeClass().addClass('fa-solid fa-pen-to-square').addClass('estilosUploadIcon');

    /*Agrega un icono a los input tipo password para que puedan ver su contraseña*/
    agregaIconoPassword = function(pintar) {
        $("input[type='password']").each(function() {
            var anchoPassword = $(this).width();
            var altoPassword = $(this).height();

            var posYpassword = altoPassword - $(this).position().top - 25;
            var posXpassword = $(this).position().left + anchoPassword - 30;

            var xPos = posXpassword + 'px';
            var yPos = posYpassword + 'px';
            if (pintar == true) {
                $(this).after('<div style="position:absolute"><label class="verPassword" style="position:relative; top:' + yPos + ';left:' + xPos + '";><span class="iconoVer" ></span></label></div>');

            } else {

                $(this).next().children().css({ top: yPos, left: xPos });

            }


        });
    }



    $("body").on("mousedown ", "span.iconoVer", function() {
        $(this).parent().parent().prev().attr('type', 'text');

    }).on("mouseup mouseleave ", "span.iconoVer", function() {
        $(this).parent().parent().prev().attr('type', 'password');

    });

    $(window).resize(function() {
        setTimeout(function() {
            agregaIconoPassword(false);
        }, 250);


    });
    agregaIconoPassword(true);


    ////////////////////////////////////////////////////////////////////////////////////////////////


    /*ROLES Y PERMISOS*/
    /*Aplica un efecto al renglon al agregar o editar un registro*/
    resaltar = function() {

        $('tbody tr:first-child').css("backgroundColor", "#999");
        $('table.table tr:first-child td').css('color', 'white');

        $("tbody tr:first-child").animate({
            backgroundColor: "#fff"
        }, {
            duration: 3000,
            queue: false
        });

        $("table.table tr:first-child td").animate({
            color: '#393C3E'
        }, {
            duration: 3000,
            queue: false

        });

        $('tbody tr:first-child').hover(function() {
            $(this).css("backgroundColor", "#eee");
        }, function() {
            $(this).css("backgroundColor", "#fff");
        });
    }

    huboCambio = function() {
        alert("cambio");
    }


    ////////////////
    /*LOGISTICA*/

    scrollArriba = function() {  
        $('html,body').animate({ scrollTop: $(".navbar.navbar-default.navbar-static-top").offset().top });

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    /*PLANES*/
    /*Ajustar ancho en selects 'institucion y sede' de búsqueda de planes*/
    $("div[id*='institucion_panel'] .ui-selectonemenu-items-wrapper").addClass('institucion');
    $("div[id*='sedePlan_panel'] .ui-selectonemenu-items-wrapper").addClass('sedePlanes');
    /*Cambiar el tipo de dato de los spinner, por defecto son text*/
    $('.input.ui-spinner-input.ui-inputfield.ui-state-default.ui-corner-all').attr('type', 'number');
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**ENCUESTAS**/

    /*Validaciones al crear encuesta */
    validarCreacionEncuesta = function() {
        var camposCorrectos = true;
        var nombre = '';
        var instrucciones = '';
        var contexto = '';
        var nivelEvaluacion = '';
        var dirigidoa = '';
        var numeroPreguntas = 0;
        nombre = $("input[type='text'][id$='id_nombre']").val();
        instrucciones = $("input[type='text'][id$='id_instrucciones']").val();
        contexto = $("label[id$='id_contexto_encuesta_label']").text();
        nivelEvaluacion = $("label[id$='id_tipo_encuesta_label']").text();
        dirigidoa = $("label[id$='id_objetivo_encuesta_label']").text();
        numeroPreguntas = $("table tbody[id$='dataTablePreguntas_data'] tr").length;

        if (!$("#validaNombre").length)
            $("input[type='text'][id$='id_nombre']").after('<div id="validaNombre" style="display:none" class="ui-message ui-message-error ui-widget ui-corner-all"><span class="ui-message-error-icon"></span><span class="ui-message-error-detail">Dato requerido.</span></div>');

        if (!$("#validaInstrucciones").length)
            $("input[type='text'][id$='id_instrucciones']").after('<div id="validaInstrucciones" style="display:none" class="ui-message ui-message-error ui-widget ui-corner-all"><span class="ui-message-error-icon"></span><span class="ui-message-error-detail">Dato requerido.</span></div>');

        if (!$("#validaContexto").length)
            $("div[id$='id_contexto_encuesta']").after('<div id="validaContexto" style="display:none" class="ui-message ui-message-error ui-widget ui-corner-all"><span class="ui-message-error-icon"></span><span class="ui-message-error-detail">Dato requerido.</span></div>');

        if (!$("#validaTipo").length)
            $("div[id$='id_tipo_encuesta']").after('<div id="validaTipo" style="display:none" class="ui-message ui-message-error ui-widget ui-corner-all"><span class="ui-message-error-icon"></span><span class="ui-message-error-detail">Dato requerido.</span></div>');

        if (!$("#validaObjetivo").length)
            $("div[id$='id_objetivo_encuesta']").after('<div id="validaObjetivo" style="display:none" class="ui-message ui-message-error ui-widget ui-corner-all"><span class="ui-message-error-icon"></span><span class="ui-message-error-detail">Dato requerido.</span></div>');

        if (!$("#validaNumPreguntas").length)
            $("input[type='text'][id$='idPregunta']").after('<div id="validaNumPreguntas" style="display:none" class="ui-message ui-message-error ui-widget ui-corner-all"><span class="ui-message-error-icon"></span><span class="ui-message-error-detail">Mínimo 5 preguntas.</span></div>');

        $("button[id$='btn_finalizar']").blur();

        if (!nombre) {
            $("#validaNombre").css('display', 'block');
            camposCorrectos = false;
        } else {
            $("#validaNombre").css('display', 'none');
        }
        if (!instrucciones) {
            $("#validaInstrucciones").css('display', 'block');
            camposCorrectos = false;
        } else {
            $("#validaInstrucciones").css('display', 'none');
        }
        if (contexto == 'Seleccionar') {
            $("#validaContexto").css('display', 'block');
            camposCorrectos = false;
        } else {
            $("#validaContexto").css('display', 'none');
        }

        if (nivelEvaluacion == 'Seleccionar') {
            $("#validaTipo").css('display', 'block');
            camposCorrectos = false;
        } else {
            $("#validaTipo").css('display', 'none');
        }
        if (dirigidoa == 'Seleccionar') {
            $("#validaObjetivo").css('display', 'block');
            camposCorrectos = false;
        } else {
            $("#validaObjetivo").css('display', 'none');
        }
        if (numeroPreguntas < 5) {
            $("#validaNumPreguntas").css('display', 'block');
            camposCorrectos = false;
        } else {
            $("#validaNumPreguntas").css('display', 'none');
        }


        //ENCONTRAR POSICIONES
        if (!nombre) {
            $('html,body').animate({ scrollTop: $("input[type='text'][id$='id_nombre']").offset().top - 50 });
        } else if (!instrucciones) {
            $('html,body').animate({ scrollTop: $("input[type='text'][id$='id_instrucciones']").offset().top - 50 });
        } else if (contexto == 'Seleccionar') {
            $('html,body').animate({ scrollTop: $("label[id$='id_contexto_encuesta_label']").offset().top - 50 });
        } else if (nivelEvaluacion == 'Seleccionar') {
            $('html,body').animate({ scrollTop: $("label[id$='id_tipo_encuesta_label']").offset().top - 50 });
        } else if (dirigidoa == 'Seleccionar') {
            $('html,body').animate({ scrollTop: $("label[id$='id_objetivo_encuesta_label']").offset().top - 50 });
        } else if (numeroPreguntas < 5) {
            $('html,body').animate({ scrollTop: $("input[type='text'][id$='idPregunta']").offset().top - 50 });
        }

        if (camposCorrectos == true) {
            return true;
        } else {
            return false;
        }

    }

    /*Validaciones al guardar borrador en encuestas*/
    validarBorradorEncuesta = function() {
        var camposCorrectos = true;
        var nombre = '';
        nombre = $("input[type='text'][id$='id_nombre']").val();
        if (!$("#validaNombre").length)
            $("input[type='text'][id$='id_nombre']").after('<div id="validaNombre" style="display:none" class="ui-message ui-message-error ui-widget ui-corner-all"><span class="ui-message-error-icon"></span><span class="ui-message-error-detail">Dato requerido.</span></div>');


        $("button[id$='btn_borrador']").blur();

        if (!nombre) {
            $("#validaNombre").css('display', 'block');
            camposCorrectos = false;
        } else {
            $("#validaNombre").css('display', 'none');
        }

        if (!nombre) {
            $('html,body').animate({ scrollTop: $("input[type='text'][id$='id_nombre']").offset().top - 50 });
        }

        if (camposCorrectos == true) {
            return true;
        } else {
            return false;
        }
    }



    desactivaBtnComentarios = function() {

        if ($("button[id$='btnComentarios']").length) {
            //var comentarios = $("textarea[id$='comentarios']").val();
            //if(comentarios){
            // PF('wbtnComentarios').enable();
            //}else{
            PF('wbtnComentarios').disable();
            //}
        }
    }

    limpiaComentarios = function() {

        if ($("textarea[id$='comentarios']").length) {
            //var comentarios = $("textarea[id$='comentarios']").val();
            //if(comentarios){
            // PF('wbtnComentarios').enable();
            //}else{
            $(this).val('');
            //}
        }
    }


    limpiaComentarios();
    desactivaBtnComentarios();
    verificaComentarios = function() {
        if ($("button[id$='btnComentarios']").length) {
            var comentarios = $("textarea[id$='comentarios']").val();
            if (comentarios) {
                $("#campoVacio").css('display', 'none');
                return true;
            } else {
                $("#campoVacio").css('display', 'block');
                return false;
            }
        }
    }

    $("textarea[id$='comentarios']").on('keyup', function() {
        var comentarios = $(this).val();
        if (comentarios) {
            PF('wbtnComentarios').enable();
            PF('btnAprobar').disable();
            //$("#campoVacio").css('display','none');
        } else {
            PF('btnAprobar').enable();
            PF('wbtnComentarios').disable();
        }
    });

    $("textarea[id$='comentarios']").on('focus', function() {
        var comentarios = $(this).val();
        if (comentarios) {
            PF('wbtnComentarios').enable();
            PF('btnAprobar').disable();
            //$("#campoVacio").css('display','none');
        } else {
            PF('btnAprobar').enable();
            PF('wbtnComentarios').disable();
        }
    });

    $("textarea[id$='comentarios']").on('blur', function() {
        var comentarios = $(this).val();
        if (comentarios) {
            PF('wbtnComentarios').enable();
            PF('btnAprobar').disable();
            //$("#campoVacio").css('display','none');
        } else {
            PF('btnAprobar').enable();
            PF('wbtnComentarios').disable();
        }
    });


    $("textarea[id$='comentarios']").on('keyup', function() {
        var comentarios = $(this).val();
        if (comentarios) {
            PF('wbtnComentarios').enable();
            PF('btnAprobar').disable();
            //$("#campoVacio").css('display','none');
        } else {
            PF('btnAprobar').enable();
            PF('wbtnComentarios').disable();
        }
    });





    /*Efectos para imagenes y tooltip en responder encuesta*/
    cargarEventos = function() {

        $("a img.imgEncuesta").hover(function() {

            $(this).removeClass('inactiva');
            $(this).addClass('activa');

            $(this).prev().removeClass('inactiva');
            $(this).prev().addClass('activa');


            $(this).animate({
                height: '105px',
                width: '105px'
            }, { duration: 200, queue: false });
            $("a img.imgEncuesta.inactiva").animate({
                opacity: '0.4'
            }, { duration: 200, queue: false });

            $("div.contenedorTooltip.activa").animate({
                opacity: '1',

            }, {
                duration: 200,
                queue: false

            });

            /*$("div.contenedorTooltip .propiedadTooltip").animate({
                    fontSize: '16px',
                    paddingLeft: '8px',
                    paddingRight: '11px',
                    top: '-34px',
                    width: '114px'
               },{
                    duration: 200, queue: false 

               });*/
        }, function() {
            $(this).addClass('inactiva');
            $(this).removeClass('activa');

            $(this).prev().addClass('inactiva');
            $(this).prev().removeClass('activa');

            $(this).animate({
                height: '70px',
                width: '70px'
            }, { duration: 200, queue: false });

            $("a img.imgEncuesta.inactiva").animate({
                opacity: '1'
            }, { duration: 200, queue: false });

            $("div.contenedorTooltip").animate({
                opacity: '0'
            }, {
                duration: 200,
                queue: false

            });
            /*
                $("div.contenedorTooltip .propiedadTooltip").animate({
                    fontSize: '10px',
                    paddingLeft: '6px',
                    paddingRight: '6px',
                    width: '77px',
                    top: '-18px'

               },{
                    duration: 200, queue: false 
               });*/
        });
    }
    cargarEventos();

    /*Tooltip para los botones del 'celleditor' de primefaces */
    $('body').append("<div class='tooltipPersonalizado ui-tooltip ui-widget ui-tooltip-top' style='display:none, opacity:0, z-index:1005'> <div class='ui-tooltip-arrow'></div> <div class='ui-tooltip-text ui-shadow ui-corner-all textoTooltip'>Editar</div></div>");
    var posXtooltip = 0;
    var posYtooltip = 0;

    /*Para el boton de Editar*/
    $("body").on("mouseenter ", ".ui-row-editor.ui-helper-clearfix.flexible span.ui-icon.ui-icon-pencil", function() {
        posYtooltip = $(this).offset().top;
        posXtooltip = $(this).offset().left;
        $('.textoTooltip').text('Editar');
        $('.tooltipPersonalizado').css({
            display: 'block',
            position: 'absolute',
            top: posYtooltip - 37,
            left: posXtooltip - 15
        });
        $('.tooltipPersonalizado').animate({
            opacity: '1'
        }, {
            duration: 300,
            queue: false

        });;
    }).on("mouseleave", ".ui-row-editor.ui-helper-clearfix.flexible span.ui-icon.ui-icon-pencil", function() {
        $('.tooltipPersonalizado').animate({
            opacity: '0'
        }, {
            duration: 300,
            queue: false

        });
        $('.tooltipPersonalizado').css('display', 'none');
    });

    /*Para el boton de aceptar*/
    $("body").on("mouseenter ", ".ui-row-editor.ui-helper-clearfix.flexible span.ui-icon.ui-icon-check", function() {
        posYtooltip = $(this).offset().top;
        posXtooltip = $(this).offset().left;
        $('.textoTooltip').text('Aceptar');
        $('.tooltipPersonalizado').css({
            display: 'block',
            position: 'absolute',
            top: posYtooltip - 37,
            left: posXtooltip - 20
        });
        $('.tooltipPersonalizado').animate({
            opacity: '1'
        }, {
            duration: 300,
            queue: false

        });;
    }).on("mouseleave", ".ui-row-editor.ui-helper-clearfix.flexible span.ui-icon.ui-icon-check", function() {
        $('.tooltipPersonalizado').animate({
            opacity: '0'
        }, {
            duration: 300,
            queue: false

        });
        $('.tooltipPersonalizado').css('display', 'none');
    });

    /*Para el boton de cancelar*/
    $("body").on("mouseenter ", ".ui-row-editor.ui-helper-clearfix.flexible span.ui-icon.ui-icon-close", function() {
        posYtooltip = $(this).offset().top;
        posXtooltip = $(this).offset().left;
        $('.textoTooltip').text('Cancelar');
        $('.tooltipPersonalizado').css({
            display: 'block',
            position: 'absolute',
            top: posYtooltip - 37,
            left: posXtooltip - 25
        });
        $('.tooltipPersonalizado').animate({
            opacity: '1'
        }, {
            duration: 300,
            queue: false

        });;
    }).on("mouseleave", ".ui-row-editor.ui-helper-clearfix.flexible span.ui-icon.ui-icon-close", function() {
        $('.tooltipPersonalizado').animate({
            opacity: '0'
        }, {
            duration: 300,
            queue: false

        });
        $('.tooltipPersonalizado').css('display', 'none');
    });


    /*El tooltip se elimina cuando se les da click a los botones de aceptar y cancelar*/
    $("body").on("click ", ".ui-row-editor.ui-helper-clearfix.flexible span.ui-icon.ui-icon-check, .ui-row-editor.ui-helper-clearfix.flexible span.ui-icon.ui-icon-close", function() {
        $('.tooltipPersonalizado').css('display', 'none');
    });


    ////////////////////////////////////////////////////////////////////////////////////////////////



//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
/*
    $(window).bind("load resize", function() {
        var body = $('body');
        body.css('backgroundColor','black');

        var topOffset = 50;
        var width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        var height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });
    */
/*
    var url = window.location;
    // var element = $('ul.nav a').filter(function() {
    //     return this.href == url;
    // }).addClass('active').parent().parent().addClass('in').parent();
    var element = $('ul.nav a').filter(function() {
        return this.href == url;
    }).addClass('active').parent();

    while (true) {
        if (element.is('li')) {
            element = element.parent().addClass('in').parent();
        } else {
            break;
        }
    }
*/


mnuCollapsed = localStorage.getItem('mnuCollapsed') || 0;

$('#side-menu > li:first-child i').click(function() {
    $('#wrapper').toggleClass('menu-collapsed');
    if (mnuCollapsed == 0) {
        $('#side-menu li:nth-child(n+2) a').attr({ 'data-toggle': 'tooltip', 'data-placement': 'right' }).tooltip();
        mnuCollapsed = 1;
    } else {
        $('#side-menu li:nth-child(n+2) a').tooltip('destroy');
        mnuCollapsed = 0;
    }
    localStorage.setItem('mnuCollapsed', mnuCollapsed);
});

     correccionSelectores = function() {
        setTimeout(function() {
            $('.ui-corner-all .ui-icon-triangle-1-s').attr('class', 'fa fa-angle-down');
        }, 500);
    };


    if (mnuCollapsed == 1) {
        $('#side-menu li:nth-child(n+2) a').attr({ 'data-toggle': 'tooltip', 'data-placement': 'right' }).tooltip();
        $('#wrapper').attr('class', 'menu-collapsed');
    } else {
        $('#side-menu li:nth-child(n+2) a').tooltip('destroy');
        $('#wrapper').removeClass('menu-collapsed');
    }



    primerBread = $('.breadcrumb li:nth-child(2)').html();
    $('#side-menu > li > a[title=\'' + primerBread + '\'],#side-menu > li > a[data-original-title=\'' + primerBread + '\']').addClass('active').next().addClass('in').parent().addClass('active');

    segundoBread = $('.breadcrumb li:nth-child(3)').html();
    $('#side-menu ul.nav-second-level > li > a[title=\'' + segundoBread + '\'],#side-menu ul.nav-second-level > li > a[data-original-title=\'' + segundoBread + '\']').addClass('active').next().addClass('in').parent().addClass('active');

    tercerBread = $('.breadcrumb li:nth-child(4)').html();
    $('#side-menu ul.nav-third-level > li > a[title=\'' + tercerBread + '\'],#side-menu ul.nav-third-level > li > a[data-original-title=\'' + tercerBread + '\']').addClass('active').next().addClass('in').parent().addClass('active');
/*
    setTimeout(function() {
        if ($('#side-menu').height() > $('#page-wrapper').height()) {
            $('#page-wrapper').css('min-height', $('#side-menu').height());
        }
    }, 600);*/
   /* PF('dialogLayout').hide();*/
});
  