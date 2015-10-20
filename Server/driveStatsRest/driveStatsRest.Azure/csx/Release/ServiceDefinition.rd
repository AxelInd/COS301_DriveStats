<?xml version="1.0" encoding="utf-8"?>
<serviceModel xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="driveStatsRest.Azure" generation="1" functional="0" release="0" Id="e8c11421-0202-4c9e-9b11-f77037bd65f1" dslVersion="1.2.0.0" xmlns="http://schemas.microsoft.com/dsltools/RDSM">
  <groups>
    <group name="driveStatsRest.AzureGroup" generation="1" functional="0" release="0">
      <componentports>
        <inPort name="driveStatsRest:Endpoint1" protocol="http">
          <inToChannel>
            <lBChannelMoniker name="/driveStatsRest.Azure/driveStatsRest.AzureGroup/LB:driveStatsRest:Endpoint1" />
          </inToChannel>
        </inPort>
      </componentports>
      <settings>
        <aCS name="driveStatsRest:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/driveStatsRest.Azure/driveStatsRest.AzureGroup/MapdriveStatsRest:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </maps>
        </aCS>
        <aCS name="driveStatsRestInstances" defaultValue="[1,1,1]">
          <maps>
            <mapMoniker name="/driveStatsRest.Azure/driveStatsRest.AzureGroup/MapdriveStatsRestInstances" />
          </maps>
        </aCS>
      </settings>
      <channels>
        <lBChannel name="LB:driveStatsRest:Endpoint1">
          <toPorts>
            <inPortMoniker name="/driveStatsRest.Azure/driveStatsRest.AzureGroup/driveStatsRest/Endpoint1" />
          </toPorts>
        </lBChannel>
      </channels>
      <maps>
        <map name="MapdriveStatsRest:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/driveStatsRest.Azure/driveStatsRest.AzureGroup/driveStatsRest/Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </setting>
        </map>
        <map name="MapdriveStatsRestInstances" kind="Identity">
          <setting>
            <sCSPolicyIDMoniker name="/driveStatsRest.Azure/driveStatsRest.AzureGroup/driveStatsRestInstances" />
          </setting>
        </map>
      </maps>
      <components>
        <groupHascomponents>
          <role name="driveStatsRest" generation="1" functional="0" release="0" software="C:\Users\Zander\Documents\GitHub\COS301_DriveStats\Server\driveStatsRest\driveStatsRest.Azure\csx\Release\roles\driveStatsRest" entryPoint="base\x64\WaHostBootstrapper.exe" parameters="base\x64\WaIISHost.exe " memIndex="-1" hostingEnvironment="frontendadmin" hostingEnvironmentVersion="2">
            <componentports>
              <inPort name="Endpoint1" protocol="http" portRanges="80" />
            </componentports>
            <settings>
              <aCS name="Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="" />
              <aCS name="__ModelData" defaultValue="&lt;m role=&quot;driveStatsRest&quot; xmlns=&quot;urn:azure:m:v1&quot;&gt;&lt;r name=&quot;driveStatsRest&quot;&gt;&lt;e name=&quot;Endpoint1&quot; /&gt;&lt;/r&gt;&lt;/m&gt;" />
            </settings>
            <resourcereferences>
              <resourceReference name="DiagnosticStore" defaultAmount="[4096,4096,4096]" defaultSticky="true" kind="Directory" />
              <resourceReference name="EventStore" defaultAmount="[1000,1000,1000]" defaultSticky="false" kind="LogStore" />
            </resourcereferences>
          </role>
          <sCSPolicy>
            <sCSPolicyIDMoniker name="/driveStatsRest.Azure/driveStatsRest.AzureGroup/driveStatsRestInstances" />
            <sCSPolicyUpdateDomainMoniker name="/driveStatsRest.Azure/driveStatsRest.AzureGroup/driveStatsRestUpgradeDomains" />
            <sCSPolicyFaultDomainMoniker name="/driveStatsRest.Azure/driveStatsRest.AzureGroup/driveStatsRestFaultDomains" />
          </sCSPolicy>
        </groupHascomponents>
      </components>
      <sCSPolicy>
        <sCSPolicyUpdateDomain name="driveStatsRestUpgradeDomains" defaultPolicy="[5,5,5]" />
        <sCSPolicyFaultDomain name="driveStatsRestFaultDomains" defaultPolicy="[2,2,2]" />
        <sCSPolicyID name="driveStatsRestInstances" defaultPolicy="[1,1,1]" />
      </sCSPolicy>
    </group>
  </groups>
  <implements>
    <implementation Id="ea4f6c3a-c7ed-432b-b594-f4b772c209ba" ref="Microsoft.RedDog.Contract\ServiceContract\driveStatsRest.AzureContract@ServiceDefinition">
      <interfacereferences>
        <interfaceReference Id="142625eb-d4e5-4c01-b227-09c73f9685ba" ref="Microsoft.RedDog.Contract\Interface\driveStatsRest:Endpoint1@ServiceDefinition">
          <inPort>
            <inPortMoniker name="/driveStatsRest.Azure/driveStatsRest.AzureGroup/driveStatsRest:Endpoint1" />
          </inPort>
        </interfaceReference>
      </interfacereferences>
    </implementation>
  </implements>
</serviceModel>