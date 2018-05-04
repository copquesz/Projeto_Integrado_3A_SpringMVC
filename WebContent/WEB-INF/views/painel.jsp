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
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <div class="col-md-6">
                            <div class="panel panel-default padding-10">
                                <div class="panel-heading">
                                   <i class="fas fa-bullhorn"></i> Senha:
                                </div>
                                <c:if test="${not empty atendimento.senha.id}">
                                    <div class="panel-body custom-background-blue border-radius">
                                        <center><span class="custom-painel-text">${atendimento.senha.id}</span></center>                               
                                    </div>
                                </c:if>

                                <c:if test="${empty atendimento.senha.id}">
                                    <div class="panel-body custom-background-blue border-radius">
                                        <center><span class="custom-painel-text" style="color: red;"> ---- </span></center>                               
                                    </div>
                                </c:if>

                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="panel panel-default padding-10">
                                <div class="panel-heading">
                                   <i class="fas fa-tv"></i> Guichê:
                                </div>
                                <c:if test="${not empty atendimento.subServico.ordem}">
                                    <div class="panel-body custom-background-blue border-radius">
                                        <center><span class="custom-painel-text">${atendimento.subServico.ordem}</span></center>                               
                                    </div>
                                </c:if>
                                <c:if test="${empty atendimento.subServico.ordem}">
                                    <div class="panel-body custom-background-blue border-radius">
                                        <center><span class="custom-painel-text" style="color: red;"> ---- </span></center>                               
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="panel panel-default padding-10">
                                <div class="panel-heading">
                                   <i class="fas fa-user"></i> Serviço:
                                </div>
                                <c:if test="${not empty atendimento.servico.nome}">
                                    <div class="panel-body custom-background-blue border-radius">
                                        <center><span class="custom-painel-text">${atendimento.servico.nome}</span></center>                               
                                    </div>
                                </c:if>
                                <c:if test="${empty atendimento.servico.nome}">
                                    <div class="panel-body custom-background-blue border-radius">
                                        <center><span class="custom-painel-text" style="color: red;"> ---- </span></center>                               
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="panel panel-default padding-10">
                                <div class="panel-heading">
                                   <i class="fas fa-user"></i> Sub-Serviço:
                                </div>
                                <c:if test="${not empty atendimento.subServico.nome}">
                                    <div class="panel-body custom-background-blue border-radius">
                                        <center><span class="custom-painel-text">${atendimento.subServico.nome}</span></center>                               
                                    </div>
                                </c:if>
                                <c:if test="${empty atendimento.subServico.nome}">
                                    <div class="panel-body custom-background-blue border-radius">
                                        <center><span class="custom-painel-text" style="color: red;"> ---- </span></center>                               
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    
                </div> <!-- /. ROW  -->
                <div class="row">
                    <div class="col-md-6 col-sm-12 col-xs-12"">

                        <div class="panel panel-default" style="min-height: 420px;">
                            <div class="panel-heading">
                                Previsão de Atendimento
                            </div> 
                            <div class="panel-body">
                                <c:if test="${not empty senhas}">
                                    <div class="table-responsive">
                                        <table class="table table-striped table-bordered table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Senha</th>
                                                    <th>Tempo de Espera</th>  
                                                    <th>Previsão de Atendimento</th>                                                
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="senha" items="${senhas}">
                                                    <tr>
                                                        <td>${senha.id}</td>
                                                        <td>${senha.tempoEspera}</td>
                                                        <td><fmt:formatDate value="${senha.previsao}" type="both" dateStyle="short"/></span></td>
                                                    </tr>
                                                </c:forEach>                                            

                                            </tbody>
                                        </table>
                                    </div>
                                </c:if>
                                <c:if test="${empty senhas}">                                    
                                    <div class="panel panel-warning">
                                        <div class="panel-heading">
                                            Não há senha aguardando atendimento!
                                        </div>                                        
                                    </div>                                    
                                </c:if>
                            </div>
                        </div>

                    </div>

                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <div class="panel panel-default" style="min-height: 420px;">
                            <div class="panel-heading">
                                Histórico de Chamadas
                            </div>
                            <div class="panel-body">
                                <c:if test="${not empty atendimentos}">
                                    <div class="list-group">
                                        <c:forEach var="atendimento2" items="${atendimentos}">
                                            <a href="#" class="list-group-item" style="pointer-events: none;">
                                                <span class="badge">${atendimento2.tempoAtras} atrás</span>                                        
                                                <b>Senha: </b> ${atendimento2.senha.id}
                                                <br>                                        
                                                <b>Sub-Serviço: </b>${atendimento2.subServico.nome}
                                                <br>                                        
                                                <b>Guichê: </b>${atendimento2.subServico.ordem}
                                            </a>       
                                        </c:forEach>                                                                                              
                                    </div>  
                                </c:if>   
                                <c:if test="${empty atendimentos}">                                    
                                    <div class="panel panel-warning">
                                        <div class="panel-heading">
                                            Nenhuma senha foi atendida ainda!
                                        </div>                                        
                                    </div>                                    
                                </c:if>                          
                            </div>
                        </div>

                    </div>

                </div> <!-- /. ROW  -->

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
