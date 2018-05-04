﻿<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" %>
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
                                O atendimento foi finalizado com sucesso!
                            </div>
                            <div class="panel-body">
                                <p>Dados do atendimento:</p>
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>Sub-Serviço</th>
                                                <th>Início</th>
                                                <th>Fim</th>
                                                <th>Tempo de Atendimento</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="atendimento" items="${atendimentos}">
                                                <tr>
                                                    <td>${atendimento.subServico.nome}</td>
                                                    <td><fmt:formatDate value="${atendimento.inicio}" type="both" dateStyle="short"/></td>
                                                    <td><fmt:formatDate value="${atendimento.fim}" type="both" dateStyle="short"/></td>
                                                    <td>${atendimento.tempoAtendimento}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>                                
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