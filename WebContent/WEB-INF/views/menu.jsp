<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<nav class="navbar navbar-default top-navbar" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="index">Cartório USJT</a>
    </div>

    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="login"><i class="fa fa-user fa-fw"></i> Área Administrativa</a>
                </li>                        
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    </nav>
    <!--/. NAV TOP  -->
    <nav class="navbar-default navbar-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav" id="main-menu">

            <li>
                <a href="index"><i class="fas fa-desktop"></i> Painel Eletrônico</a>
            </li>
            <li>
                <a href="#"><i class="fas fa-list-ul"></i> Serviços Disponíveis<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li>
                        <a href="#">Registro Civil</a>
                    </li>
                    <li>
                        <a href="#">Registro Imobiliário</a>
                    </li> 
                    <li>
                        <a href="#">Registro de Títulos</a>
                    </li> 
                    <li>
                        <a href="#">Tabelionatos de Protestos</a>
                    </li>     
                    <li>
                        <a href="#">Tabelionatos de Notas</a>
                    </li>                        
                </ul>
            </li>
            <li>
                <a href="listar-servicos"><i class="far fa-hand-point-down"></i> Emitir Senha</a>
            </li>  
            <li>
                <a href="consultar-senha"><i class="far fa-hand-point-down"></i> Consultar Senha</a>
            </li>             
            <c:if test="${isSenhaEmAtendimento}">
                <li>
                    <a href="senha-atual" style="color: red;"><i class="far fa-hand-point-down"></i>Senha em Atendimento</a>
                </li>
            </c:if>
            <c:if test="${not isSenhaEmAtendimento}">
                <c:if test="${haveSenha}">
                    <li>
                        <a href="proxima-senha" style="color: green;"><i class="far fa-hand-point-down"></i>Atender Próxima Senha</a>
                    </li>
                </c:if>
                <c:if test="${not haveSenha}">
                    <li>
                        <a href="proxima-senha" style="color: grey; pointer-events: none;"><i class="far fa-hand-point-down"></i>Atender Próxima Senha</a>
                    </li>
                </c:if>
            </c:if>

        </ul>

    </div>

</nav><!-- /. NAV SIDE  -->
    
    
