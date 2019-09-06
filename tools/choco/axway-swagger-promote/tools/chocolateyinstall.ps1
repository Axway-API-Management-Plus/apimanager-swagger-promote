$ErrorActionPreference = 'Stop'; # stop on all errors
$toolsDir   = "$(Split-Path -parent $MyInvocation.MyCommand.Definition)"
$fileLocation = Join-Path $toolsDir 'apimanager-swagger-promote-1.6.2.zip'

$packageArgs = @{
  packageName   = $env:ChocolateyPackageName
  unzipLocation = $toolsDir
  file          = $fileLocation

  softwareName  = 'axway-swagger-promote'
  checksum      = '1CF9B7D060A19DAD8141E9DB49AAFFF2'
  checksumType  = 'sha256'
}

Get-ChocolateyUnzip "$fileLocation" $toolsDir
Install-BinFile -Name "api-import" -Path "$toolsDir\swagger-promote-$env:chocolateyPackageVersion\scripts\api-import.bat" -Command "choco"
Install-BinFile -Name "api-export" -Path "$toolsDir\swagger-promote-$env:chocolateyPackageVersion\scripts\api-export.bat" -Command "choco"

Write-Output "------------------------------------------------------------------------"
Write-Output "Axway Swagger-Promote: $env:chocolateyPackageVersion has been installed."
Write-Output ""
Write-Output "Execute: 'api-import' to import new APIs"
Write-Output "Execute: 'api-export' to export existing APIs"
Write-Output "------------------------------------------------------------------------"
