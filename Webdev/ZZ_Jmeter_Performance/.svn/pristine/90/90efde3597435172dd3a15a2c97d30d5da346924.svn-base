<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="yes" encoding="UTF-8" doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN" />


<!--
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
-->
<xsl:key name="groups" match="httpSample" use="@lb"/>
<xsl:key name="times" match="httpSample" use="@t"/>

<xsl:template match="testResults">
	<html>
		<head>
			<title>Lotto Website Load Test Results</title>
			<style type="text/css">
				body {
					font:normal 68% verdana,arial,helvetica;
					color:#000000;
				}
				table tr td, table tr th {
					font-size: 68%;
				}
				table.details tr th{
					font-weight: bold;
					text-align:center;
					background:#a6caf0;
					white-space: nowrap;
				}
				table.details tr td{
					text-align:center;
					background:#eeeee0;
					white-space: nowrap;
				}
				h1 {
					margin: 0px 0px 5px; font: 165% verdana,arial,helvetica
				}
				h2 {
					margin-top: 1em; margin-bottom: 0.5em; font: bold 125% verdana,arial,helvetica
				}
				h3 {
					margin-bottom: 0.5em; font: bold 115% verdana,arial,helvetica
				}
				.Failure {
					font-weight:bold; color:red;
				}
				.Failure1 {
					font-weight:bold; color:brown;
				}
			</style>
		</head>
		<body>
	
			<h2>Lotto Backend Load Test Summary</h2>
			
			<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
				<tr valign="top">
					<th>API Name</th>
					<th>Thread(s)</th>
					<th>Total Time(in Ms)</th>
					<th>Avg.Time Base line(in Ms)</th>
					<th>Avg. Time(in Ms)</th>
					<th>Min. Time(in Ms)</th>
					<th>Max. Time(in Ms)</th>
					<th>Success rate(%)</th>
					<th>Failure rate(%)</th>
				</tr>

				<xsl:for-each select="//httpSample[generate-id()=generate-id(key('groups',@lb)[1])]">
				<xsl:choose>
				<xsl:when test="(sum(key('groups', @lb)/@t) div count(key('groups',@lb)) &gt; 500) and (@lb != 'GenerateKey')">
				<tr class='Failure'>
					<td><xsl:value-of select="@lb"/></td>
					<td><xsl:value-of select="count(key('groups',@lb))"/></td>
					<td><xsl:value-of select="sum(key('groups', @lb)/@t)"/></td>
					<td>75</td>
					<td><xsl:value-of select="sum(key('groups', @lb)/@t) div count(key('groups',@lb))"/></td>
					
										
					<xsl:for-each select="key('groups',@lb)/@t">
						<xsl:sort select="." data-type="number" order="ascending"/>
							<xsl:if test="position() = 1">
								<td><xsl:value-of select="."/></td>
							</xsl:if>
					</xsl:for-each>
					<xsl:for-each select="key('groups',@lb)/@t">
						<xsl:sort select="." data-type="number" order="ascending"/>
							<xsl:if test="position() = last()">
								<td><xsl:value-of select="."/></td>
							</xsl:if>
					</xsl:for-each>

					<td><xsl:value-of select="count(key('groups',@lb)[attribute::s='true']) div count(key('groups',@lb)) * 100"/>%</td>	
					<td><xsl:value-of select="count(key('groups',@lb)[attribute::s='false']) div count(key('groups',@lb)) * 100"/>%</td>
					
				</tr>
				</xsl:when>
				
				<xsl:when test="(count(key('groups',@lb)[attribute::s='false']) div count(key('groups',@lb)) > 0)">
				<tr class='Failure1'>
					<td><xsl:value-of select="@lb"/></td>
					<td><xsl:value-of select="count(key('groups',@lb))"/></td>
					<td><xsl:value-of select="sum(key('groups', @lb)/@t)"/></td>
					<td>75</td>
					<td><xsl:value-of select="sum(key('groups', @lb)/@t) div count(key('groups',@lb))"/></td>
					
										
					<xsl:for-each select="key('groups',@lb)/@t">
						<xsl:sort select="." data-type="number" order="ascending"/>
							<xsl:if test="position() = 1">
								<td><xsl:value-of select="."/></td>
							</xsl:if>
					</xsl:for-each>
					<xsl:for-each select="key('groups',@lb)/@t">
						<xsl:sort select="." data-type="number" order="ascending"/>
							<xsl:if test="position() = last()">
								<td><xsl:value-of select="."/></td>
							</xsl:if>
					</xsl:for-each>

					<td><xsl:value-of select="count(key('groups',@lb)[attribute::s='true']) div count(key('groups',@lb)) * 100"/>%</td>	
					<td><xsl:value-of select="count(key('groups',@lb)[attribute::s='false']) div count(key('groups',@lb)) * 100"/>%</td>
				</tr>
				</xsl:when>
				<xsl:when test="(@lb = 'GenerateKey')">
				<tr>
					<td><xsl:value-of select="@lb"/></td>
					<td><xsl:value-of select="count(key('groups',@lb))"/></td>
					<td><xsl:value-of select="sum(key('groups', @lb)/@t)"/></td>
					<td>NA</td>
					<td><xsl:value-of select="sum(key('groups', @lb)/@t) div count(key('groups',@lb))"/></td>
					
										
					<xsl:for-each select="key('groups',@lb)/@t">
						<xsl:sort select="." data-type="number" order="ascending"/>
							<xsl:if test="position() = 1">
								<td><xsl:value-of select="."/></td>
							</xsl:if>
					</xsl:for-each>
					<xsl:for-each select="key('groups',@lb)/@t">
						<xsl:sort select="." data-type="number" order="ascending"/>
							<xsl:if test="position() = last()">
								<td><xsl:value-of select="."/></td>
							</xsl:if>
					</xsl:for-each>

					<td><xsl:value-of select="count(key('groups',@lb)[attribute::s='true']) div count(key('groups',@lb)) * 100"/>%</td>	
					<td><xsl:value-of select="count(key('groups',@lb)[attribute::s='false']) div count(key('groups',@lb)) * 100"/>%</td>
			  </tr>
				</xsl:when>
				
				<xsl:otherwise>
				<tr>
					<td><xsl:value-of select="@lb"/></td>
					<td><xsl:value-of select="count(key('groups',@lb))"/></td>
					<td><xsl:value-of select="sum(key('groups', @lb)/@t)"/></td>
					<td>75</td>
					<td><xsl:value-of select="sum(key('groups', @lb)/@t) div count(key('groups',@lb))"/></td>
					
										
					<xsl:for-each select="key('groups',@lb)/@t">
						<xsl:sort select="." data-type="number" order="ascending"/>
							<xsl:if test="position() = 1">
								<td><xsl:value-of select="."/></td>
							</xsl:if>
					</xsl:for-each>
					<xsl:for-each select="key('groups',@lb)/@t">
						<xsl:sort select="." data-type="number" order="ascending"/>
							<xsl:if test="position() = last()">
								<td><xsl:value-of select="."/></td>
							</xsl:if>
					</xsl:for-each>

					<td><xsl:value-of select="count(key('groups',@lb)[attribute::s='true']) div count(key('groups',@lb)) * 100"/>%</td>	
					<td><xsl:value-of select="count(key('groups',@lb)[attribute::s='false']) div count(key('groups',@lb)) * 100"/>%</td>
					
				</tr>
				</xsl:otherwise>
				</xsl:choose>
				</xsl:for-each>
			</table>


		</body>
	</html>
</xsl:template>
	
</xsl:stylesheet>