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
                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <div class="panel panel-default padding-10">
                            <div class="panel-heading">
                               <i class="fas fa-bullhorn"></i> Senha:
                            </div>
                            <div class="panel-body custom-background-blue border-radius">
                                <center><span class="custom-painel-text">${senha.id}</span></center>                               
                            </div>
                        </div>                       
                    </div>
                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <div class="panel panel-default padding-10">
                            <div class="panel-heading">
                               <i class="fas fa-tv"></i> Serviço:
                            </div>
                            <div class="panel-body custom-background-blue border-radius">
                                <center><span class="custom-painel-text">${servico.nome}</span></center>                               
                            </div>
                        </div>                        
                    </div>
                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <div class="panel panel-default padding-10">
                            <div class="panel-heading">
                               <i class="fas fa-bullhorn"></i> Emissão:
                            </div>
                            <div class="panel-body custom-background-blue border-radius">
                                <center><span class="custom-painel-text"><fmt:formatDate value="${senha.emissao}" type="both" dateStyle="short"/></span></center>                               
                            </div>
                        </div>                       
                    </div>
                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <div class="panel panel-default padding-10">
                            <div class="panel-heading">
                               <i class="fas fa-bullhorn"></i> Previsao de Atendimento:
                            </div>
                            <div class="panel-body custom-background-blue border-radius">                               

                                <c:choose>
         
                                    <c:when test="${status1}">
                                        <center><span class="custom-painel-text"><fmt:formatDate value="${senha.previsao}" type="both" dateStyle="short"/></span></center>
                                    </c:when>
                                     
                                    <c:otherwise>
                                        <center><span class="custom-painel-text">-----</span></center>
                                    </c:otherwise>

                                </c:choose>

                            </div>
                        </div>                       
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="panel panel-default padding-10">
                            <div class="panel-heading">
                               <i class="fas fa-bullhorn"></i> Status:
                            </div>
                            <div class="panel-body custom-background-blue border-radius">
                                <center>

                                    <c:choose>
         
                                        <c:when test = "${status1}">
                                            <span class="custom-painel-text" style="color: yellow">Aguardando Atendimento</span>
                                        </c:when>

                                        <c:when test = "${status2}">
                                            <span class="custom-painel-text" style="color: green">Em Atendimento</span>
                                        </c:when>
                                         
                                        <c:otherwise>
                                            <span class="custom-painel-text" style="color: red">Finalizado</span>
                                        </c:otherwise>

                                    </c:choose>

                                </center>                               
                            </div>
                        </div>                       
                    </div>
                </div> <!-- /. ROW  -->
                <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">

                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Tempo de Atendimento
                            </div> 
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th>SubServiço</th>
                                                <th>Status</th>
                                                <th>Início do Atendimento</th>
                                                <th>Fim do Atendimento</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="atendimento" items="${atendimentos}">
                                                <tr>
                                                    <td>${atendimento.subServico.nome}</td>
                                                    <td>${atendimento.status}</td>
                                                    <c:if test="${empty atendimento.inicio}">
                                                        <td>-----</td>
                                                    </c:if>
                                                    <c:if test="${not empty atendimento.inicio}">
                                                        <td><fmt:formatDate value="${atendimento.inicio}" type="both" dateStyle="short"/></td>
                                                    </c:if>
                                                    <c:if test="${empty atendimento.fim}">
                                                        <td>-----</td>
                                                    </c:if>
                                                    <c:if test="${not empty atendimento.fim}">
                                                        <td><fmt:formatDate value="${atendimento.fim}" type="both" dateStyle="short"/></td>
                                                    </c:if>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                 <footer><p>All right reserved. Template by: <a href="http://webthemez.com">WebThemez</a> | Powered By: USJT</p></footer>
                </div>
             <!-- /. PAGE INNER  -->
            </div>
         <!-- /. PAGE WRAPPER  -->
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
