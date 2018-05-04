<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Sistema de Cartório</title>
	<!-- Bootstrap Styles-->
    <link href="css/bootstrap.css" rel="stylesheet" />
    <!-- FontAwesome Styles-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.10/css/all.css" integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg" crossorigin="anonymous">
    <!-- Custom Styles-->
    <link href="css/custom-styles.css" rel="stylesheet" />
     <!-- Google Fonts-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
    <div id="wrapper">
        <c:import url="menu.jsp" />
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-header">
                            <small><i class="fas fa-chevron-right"></i> Emitir Senha</small>
                        </h1>
                    </div>
                </div> 
                 <!-- /. ROW  -->
                <div class="row">                
                    <div class="col-md-12 col-sm-12">
                        <div class="panel panel-success">
                            <div class="panel-heading">
                                Senha emitida com sucesso!
                            </div>
                            <div class="panel-body">
                                <p>Você emitiu uma senha de atendimento para: <b>${servico.nome}</b>.<br></p>
                                <p><b>Senha: <span style="color: red;">${senha.id}</span></b></p>
                                <p><b>Tempo de Espera: <span style="color: red;">${senha.tempoEspera}</span></b></p>
                                <p><b>Previsão de Atendimento: <span style="color: red;"><fmt:formatDate type = "both" dateStyle = "medium" timeStyle = "medium" value = "${senha.previsao}"/></p></span></b></p>
                                <p>Sub-Serviços inclusos no atendimento:
                                    <ul>
                                        <c:forEach var="subServico" items="${listaSubServicos}">
                                            <li>${subServico.ordem} - ${subServico.nome}</li>
                                        </c:forEach>
                                    </ul>
                                <center><a href="index" class="btn btn-default">ACESSAR O PAINEL</a></center></p>
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>    
     <!-- /. WRAPPER  -->
    <!-- JS Scripts-->
    <!-- jQuery Js -->
    <script src="js/jquery-1.10.2.js"></script>
      <!-- Bootstrap Js -->
    <script src="js/bootstrap.min.js"></script>
    <!-- Metis Menu Js -->
    <script src="js/jquery.metisMenu.js"></script>
      <!-- Custom Js -->
    <script src="js/custom-scripts.js"></script>
    
   
</body>
</html>
