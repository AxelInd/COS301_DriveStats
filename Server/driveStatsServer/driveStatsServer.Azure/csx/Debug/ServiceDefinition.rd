<?xml version="1.0" encoding="utf-8"?>
<serviceModel xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="driveStatsServer.Azure" generation="1" functional="0" release="0" Id="f09be779-72d1-41e7-85a7-79b56a2e4b0f" dslVersion="1.2.0.0" xmlns="http://schemas.microsoft.com/dsltools/RDSM">
  <groups>
    <group name="driveStatsServer.AzureGroup" generation="1" functional="0" release="0">
      <componentports>
        <inPort name="driveStatsServer:Endpoint1" protocol="http">
          <inToChannel>
            <lBChannelMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/LB:driveStatsServer:Endpoint1" />
          </inToChannel>
        </inPort>
      </componentports>
      <settings>
        <aCS name="driveStatsServer:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/MapdriveStatsServer:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </maps>
        </aCS>
        <aCS name="driveStatsServerInstances" defaultValue="[1,1,1]">
          <maps>
            <mapMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/MapdriveStatsServerInstances" />
          </maps>
        </aCS>
      </settings>
      <channels>
        <lBChannel name="LB:driveStatsServer:Endpoint1">
          <toPorts>
            <inPortMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Endpoint1" />
          </toPorts>
        </lBChannel>
      </channels>
      <maps>
        <map name="MapdriveStatsServer:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </setting>
        </map>
        <map name="MapdriveStatsServerInstances" kind="Identity">
          <setting>
            <sCSPolicyIDMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServerInstances" />
          </setting>
        </map>
      </maps>
      <components>
        <groupHascomponents>
          <role name="driveStatsServer" generation="1" functional="0" release="0" software="C:\Users\Zander\documents\visual studio 2012\Projects\driveStatsServer\driveStatsServer.Azure\csx\Debug\roles\driveStatsServer" entryPoint="base\x64\WaHostBootstrapper.exe" parameters="base\x64\WaIISHost.exe " memIndex="-1" hostingEnvironment="frontendadmin" hostingEnvironmentVersion="2">
            <componentports>
              <inPort name="Endpoint1" protocol="http" portRanges="80" />
            </componentports>
            <settings>
              <aCS name="Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="" />
              <aCS name="__ModelData" defaultValue="&lt;m role=&quot;driveStatsServer&quot; xmlns=&quot;urn:azure:m:v1&quot;&gt;&lt;r name=&quot;driveStatsServer&quot;&gt;&lt;e name=&quot;Endpoint1&quot; /&gt;&lt;/r&gt;&lt;/m&gt;" />
            </settings>
            <resourcereferences>
              <resourceReference name="DiagnosticStore" defaultAmount="[4096,4096,4096]" defaultSticky="true" kind="Directory" />
              <resourceReference name="EventStore" defaultAmount="[1000,1000,1000]" defaultSticky="false" kind="LogStore" />
            </resourcereferences>
          </role>
          <sCSPolicy>
            <sCSPolicyIDMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServerInstances" />
            <sCSPolicyUpdateDomainMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServerUpgradeDomains" />
            <sCSPolicyFaultDomainMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServerFaultDomains" />
          </sCSPolicy>
        </groupHascomponents>
      </components>
      <sCSPolicy>
        <sCSPolicyUpdateDomain name="driveStatsServerUpgradeDomains" defaultPolicy="[5,5,5]" />
        <sCSPolicyFaultDomain name="driveStatsServerFaultDomains" defaultPolicy="[2,2,2]" />
        <sCSPolicyID name="driveStatsServerInstances" defaultPolicy="[1,1,1]" />
      </sCSPolicy>
    </group>
  </groups>
  <implements>
    <implementation Id="8accd878-bb8f-4e8b-807d-9832b36c5169" ref="Microsoft.RedDog.Contract\ServiceContract\driveStatsServer.AzureContract@ServiceDefinition">
      <interfacereferences>
        <interfaceReference Id="7963ba39-d6a8-4c2a-89c9-4ac55507b66e" ref="Microsoft.RedDog.Contract\Interface\driveStatsServer:Endpoint1@ServiceDefinition">
          <inPort>
            <inPortMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer:Endpoint1" />
          </inPort>
        </interfaceReference>
      </interfacereferences>
    </implementation>
  </implements>
</serviceModel>