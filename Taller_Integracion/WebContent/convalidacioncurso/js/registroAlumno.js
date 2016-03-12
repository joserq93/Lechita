jQuery(function($) {

	var comboDepartamento =$('#comboDepartamento').departamentoSelectize();
	var comboProvincia =$('#comboProvincia').provinciaSelectize();
	var comboDistrito = $("#comboDistrito").distritoSelectize();
	var comboEspecial = $("#comboEspecialidad").especialidadSelectize();
	var comboModalidad = $("#comboModalidad").modalidadSelectize();
	var comboSexo = $("#sexo").sexoSelectize();
	
	
	//comboDistrito.enable(comboDistrito.cargarDistrito('1'));
	comboEspecial.enable(comboEspecial.cargarEspecialidad('9'));
	comboModalidad.enable(comboModalidad.cargarModalidad());
	comboSexo.enable(comboSexo.cargarSexo());
	comboProvincia.disable();
	comboDistrito.disable();
	
	
	comboDepartamento.cargarDepartamento();
	//comboFacultad.cargarFacultades();

    comboDepartamento.on("change", function () {
        if (comboDepartamento.getValue().length) {
            $.when(comboProvincia.cargarProvincia(comboDepartamento.getValue()))
            .then(function () {
                comboProvincia.enable();
            });
        } else {
            comboProvincia.clear();
            
        }
    });

    comboProvincia.on("change", function () {
        if (comboDepartamento.getValue().length && comboProvincia.getValue().length) {
            $.when(comboDistrito.cargarDistrito(comboDepartamento.getValue(), comboProvincia.getValue()))
            .then(function () {
                comboDistrito.enable();
            });
        } else {
            comboDistrito.clear();
        }
    });
    

    comboProvincia.on("clear", function () {
       
        comboDistrito.clear();
        comboDistrito.disable();
    });

    
    comboDepartamento.on("clear", function () {
        comboProvincia.clear();
        comboProvincia.disable();
        comboDistrito.clear();
        comboDistrito.disable();
    });
    
	$(document).on("submit", '#login', function(event) {
		event.preventDefault();

		var ajaxdata = {
			'f' : 'registrarAlumno',
			'id' : $('#id').val(),
			'nom' : $('#nom').val(),
			'apepat' : $('#apepat').val(),
			'apemat' : $('#apemat').val(),
			'sexo' : $('#sexo').val(),
			'dni' : $('#dni').val(),
			'fecnac' : $('#fecnac').val(),
			'dir' : $('#dir').val(),
			'numcel' : $('#numcel').val(),
			'numcas' : $('#numcas').val(),
			'mod' : comboModalidad.getValue(),
			'dep':comboDepartamento.getValue(),
			'pro':comboProvincia.getValue(),
			'dis' : comboDistrito.getValue(),
			'especialidad' : comboEspecial.getValue()
		};

		console.log(JSON.stringify(ajaxdata));
		$.ajax({
			url : "../registrodatos",
			data : ajaxdata,
			dataType : 'json',
			type : 'POST',
			success : function(datos) {
				if (datos == "OK") {
					console.log(datos);
					 toastr["success"]("Registro Exitoso");
					 limpiar();
				} else {
					console.log(datos);
					toastr["warning"]("Error al Registrar");
				}
			}
		});
	});
	function limpiar(){
		$('#id').val(''),
		$('#nom').val(''),
		$('#apepat').val(''),
		$('#apemat').val(''),
		$('#sexo').val(''),
		$('#dni').val(''),
		$('#fecnac').val(''),
		$('#dir').val(''),
		$('#numcel').val(''),
		$('#numcas').val(''),
		comboSexo.clear(),
		comboModalidad.clear(),
		comboDistrito.clear(),
		comboEspecial.clear()
	};
	 $(document).on("keyup",'.numeros',function(){
	    	this.value = (this.value + '').replace(/[^0-9]/g, '');
	});
	 $(document).on("keyup",'.letras',function(){
	    	this.value = (this.value + '').replace(/[^a-z ñÑ]{0,100}$/, '');
	});
	 toastr.options = {
            "timeOut": 3000,
            newestOnTop: false,
            hideDuration: 500,
            "extendedTimeOut": 4000,
            "closeButton": true,
            "positionClass": "toast-bottom-right",
            "debug": false,
            "tapToDismiss": false
        };
});
