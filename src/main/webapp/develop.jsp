<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/jsp/include/taglibs.jsp"%>

<s:layout-render name="/jsp/layout/public.jsp" pageTitle="Develop">

    <s:layout-component name="contents">
        <section class="tm-section">
            <div class="container-fluid">
                <div class="row tm-2-rows-sm-swap">

                    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 col-xl-9 tm-2-rows-sm-down-1">
                        <h3 class="tm-gold-text">Develop at JCloud</h3>
                        <p>
                        Your job is to build awesome applications. Let
                        us take care of hosting them for you. We provide databases, application containers,
                        and monitoring, so you can focus on development.
                        </p>
                    </div>

					<jsp:include page="/jsp/include/sidebar.jsp"/>
                </div>
            </div>
        </section>

    </s:layout-component>
</s:layout-render>