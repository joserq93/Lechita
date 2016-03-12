<!DOCTYPE html>
<%@page import="edu.usmp.fia.taller.common.action.SessionParameters"%>
<%@page import="edu.usmp.fia.taller.common.bean.Usuario"%>
<%@page import="edu.usmp.fia.taller.common.bean.Persona"%>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>Taller Proyectos</title>
	<jsp:include page="/resources/include/header-resources.jsp"></jsp:include>
</head>

<%
	Usuario oUsuario = (Usuario) request.getSession(false).getAttribute(SessionParameters.USUARIO.text());
	Persona oPersona = oUsuario.getPersona();
%>

<style type="text/css">

.glyphicon-refresh-animate {
    -animation: spin .7s infinite linear;
    -webkit-animation: spin2 .7s infinite linear;
}

@-webkit-keyframes spin2 {
    from { -webkit-transform: rotate(0deg);}
    to { -webkit-transform: rotate(360deg);}
}

@keyframes spin {
    from { transform: scale(1) rotate(0deg);}
    to { transform: scale(1) rotate(360deg);}
}
</style>
<script type="text/javascript">
	function cambiarBoton(){
		document.getElementById('botonNormal').style.display = 'none';
		document.getElementById('botonEfecto').style.display = 'block';
		
	}
</script>

<body class="page-body skin-red">
	<div class="page-container">
		<jsp:include page="/resources/include/sidebar-menu.jsp"></jsp:include>
		<div class="main-content">
			<jsp:include page="/resources/include/profile-bar.jsp"></jsp:include>
			
			
			
			<!-- Contenido -->
			<div >
			<h2>Subir Archivo Excel</h2>
				<div class="fw-container">
		<form action="cargarExcels" method="post" enctype="multipart/form-data">
	
			
			Seleccionar Archivo : <input type="file" accept=".xls" name="filetoupload" required>
			<br/>
			<div id="botonNormal">
			<input type="submit" class="btn btn-info" value="Subir Archivo" onclick="cambiarBoton();">
			</div>
			<div id="botonEfecto" style='display:none;'>
			<button class="btn btn-lg btn-warning"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span> Loading...</button>
			</div>
		</form>
			<!-- Fin contenido -->
			<jsp:include page="/resources/include/footer.jsp"></jsp:include>
		</div>
		<jsp:include page="/resources/include/chat.jsp"></jsp:include>
		</div>
	</div>

	<jsp:include page="/resources/include/footer-resources.jsp"></jsp:include>
	
</body>
</html>