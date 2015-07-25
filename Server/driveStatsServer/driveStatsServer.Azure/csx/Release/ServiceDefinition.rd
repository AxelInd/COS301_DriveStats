<?xml version="1.0" encoding="utf-8"?>
<serviceModel xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="driveStatsServer.Azure" generation="1" functional="0" release="0" Id="bec58d61-8e1a-44e1-9b50-5df283b4d69c" dslVersion="1.2.0.0" xmlns="http://schemas.microsoft.com/dsltools/RDSM">
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
        <aCS name="Certificate|driveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.TransportValidation" defaultValue="">
          <maps>
            <mapMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/MapCertificate|driveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.TransportValidation" />
          </maps>
        </aCS>
        <aCS name="driveStatsServer:CloudToolsDiagnosticAgentVersion" defaultValue="">
          <maps>
            <mapMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/MapdriveStatsServer:CloudToolsDiagnosticAgentVersion" />
          </maps>
        </aCS>
        <aCS name="driveStatsServer:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/MapdriveStatsServer:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </maps>
        </aCS>
        <aCS name="driveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.ClientThumbprint" defaultValue="">
          <maps>
            <mapMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/MapdriveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.ClientThumbprint" />
          </maps>
        </aCS>
        <aCS name="driveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector.Enabled" defaultValue="">
          <maps>
            <mapMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/MapdriveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector.Enabled" />
          </maps>
        </aCS>
        <aCS name="driveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector.Version" defaultValue="">
          <maps>
            <mapMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/MapdriveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector.Version" />
          </maps>
        </aCS>
        <aCS name="driveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.ServerThumbprint" defaultValue="">
          <maps>
            <mapMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/MapdriveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.ServerThumbprint" />
          </maps>
        </aCS>
        <aCS name="driveStatsServer:Profiling.ProfilingConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/MapdriveStatsServer:Profiling.ProfilingConnectionString" />
          </maps>
        </aCS>
        <aCS name="driveStatsServerInstances" defaultValue="[1,1,1]">
          <maps>
            <mapMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/MapdriveStatsServerInstances" />
          </maps>
        </aCS>
      </settings>
      <channels>
        <sFSwitchChannel name="IE:driveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector">
          <toPorts>
            <inPortMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector" />
          </toPorts>
        </sFSwitchChannel>
        <sFSwitchChannel name="IE:driveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.FileUpload">
          <toPorts>
            <inPortMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Microsoft.WindowsAzure.Plugins.RemoteDebugger.FileUpload" />
          </toPorts>
        </sFSwitchChannel>
        <sFSwitchChannel name="IE:driveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.Forwarder">
          <toPorts>
            <inPortMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Microsoft.WindowsAzure.Plugins.RemoteDebugger.Forwarder" />
          </toPorts>
        </sFSwitchChannel>
        <lBChannel name="LB:driveStatsServer:Endpoint1">
          <toPorts>
            <inPortMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Endpoint1" />
          </toPorts>
        </lBChannel>
      </channels>
      <maps>
        <map name="MapCertificate|driveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.TransportValidation" kind="Identity">
          <certificate>
            <certificateMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Microsoft.WindowsAzure.Plugins.RemoteDebugger.TransportValidation" />
          </certificate>
        </map>
        <map name="MapdriveStatsServer:CloudToolsDiagnosticAgentVersion" kind="Identity">
          <setting>
            <aCSMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/CloudToolsDiagnosticAgentVersion" />
          </setting>
        </map>
        <map name="MapdriveStatsServer:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </setting>
        </map>
        <map name="MapdriveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.ClientThumbprint" kind="Identity">
          <setting>
            <aCSMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Microsoft.WindowsAzure.Plugins.RemoteDebugger.ClientThumbprint" />
          </setting>
        </map>
        <map name="MapdriveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector.Enabled" kind="Identity">
          <setting>
            <aCSMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector.Enabled" />
          </setting>
        </map>
        <map name="MapdriveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector.Version" kind="Identity">
          <setting>
            <aCSMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector.Version" />
          </setting>
        </map>
        <map name="MapdriveStatsServer:Microsoft.WindowsAzure.Plugins.RemoteDebugger.ServerThumbprint" kind="Identity">
          <setting>
            <aCSMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Microsoft.WindowsAzure.Plugins.RemoteDebugger.ServerThumbprint" />
          </setting>
        </map>
        <map name="MapdriveStatsServer:Profiling.ProfilingConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Profiling.ProfilingConnectionString" />
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
          <role name="driveStatsServer" generation="1" functional="0" release="0" software="C:\Users\Zander\documents\visual studio 2012\Projects\driveStatsServer\driveStatsServer.Azure\csx\Release\roles\driveStatsServer" entryPoint="base\x64\WaHostBootstrapper.exe" parameters="base\x64\WaIISHost.exe " memIndex="-1" hostingEnvironment="frontendadmin" hostingEnvironmentVersion="2">
            <componentports>
              <inPort name="Endpoint1" protocol="http" portRanges="80" />
            </componentports>
            <settings>
              <aCS name="CloudToolsDiagnosticAgentVersion" defaultValue="" />
              <aCS name="Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="" />
              <aCS name="Microsoft.WindowsAzure.Plugins.RemoteDebugger.ClientThumbprint" defaultValue="" />
              <aCS name="Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector.Enabled" defaultValue="" />
              <aCS name="Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector.Version" defaultValue="" />
              <aCS name="Microsoft.WindowsAzure.Plugins.RemoteDebugger.ServerThumbprint" defaultValue="" />
              <aCS name="Profiling.ProfilingConnectionString" defaultValue="" />
              <aCS name="__ModelData" defaultValue="&lt;m role=&quot;driveStatsServer&quot; xmlns=&quot;urn:azure:m:v1&quot;&gt;&lt;r name=&quot;driveStatsServer&quot;&gt;&lt;e name=&quot;Endpoint1&quot; /&gt;&lt;e name=&quot;Microsoft.WindowsAzure.Plugins.RemoteDebugger.Connector&quot; /&gt;&lt;e name=&quot;Microsoft.WindowsAzure.Plugins.RemoteDebugger.FileUpload&quot; /&gt;&lt;e name=&quot;Microsoft.WindowsAzure.Plugins.RemoteDebugger.Forwarder&quot; /&gt;&lt;/r&gt;&lt;/m&gt;" />
            </settings>
            <resourcereferences>
              <resourceReference name="DiagnosticStore" defaultAmount="[4096,4096,4096]" defaultSticky="true" kind="Directory" />
              <resourceReference name="EventStore" defaultAmount="[1000,1000,1000]" defaultSticky="false" kind="LogStore" />
            </resourcereferences>
            <storedcertificates>
              <storedCertificate name="Stored0Microsoft.WindowsAzure.Plugins.RemoteDebugger.TransportValidation" certificateStore="My" certificateLocation="System">
                <certificate>
                  <certificateMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer/Microsoft.WindowsAzure.Plugins.RemoteDebugger.TransportValidation" />
                </certificate>
              </storedCertificate>
            </storedcertificates>
            <certificates>
              <certificate name="Microsoft.WindowsAzure.Plugins.RemoteDebugger.TransportValidation" />
            </certificates>
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
    <implementation Id="8215ee6b-d2b0-4684-a279-17d256e31b8c" ref="Microsoft.RedDog.Contract\ServiceContract\driveStatsServer.AzureContract@ServiceDefinition">
      <interfacereferences>
        <interfaceReference Id="fa986ef9-6ebf-4d45-a8fa-9500ee53b818" ref="Microsoft.RedDog.Contract\Interface\driveStatsServer:Endpoint1@ServiceDefinition">
          <inPort>
            <inPortMoniker name="/driveStatsServer.Azure/driveStatsServer.AzureGroup/driveStatsServer:Endpoint1" />
          </inPort>
        </interfaceReference>
      </interfacereferences>
    </implementation>
  </implements>
</serviceModel>