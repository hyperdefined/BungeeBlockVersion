# BungeeBlockVersion
[![Downloads](https://img.shields.io/github/downloads/hyperdefined/BungeeBlockVersion/total?logo=github)](https://github.com/hyperdefined/BungeeBlockVersion/releases) [![Donate with Bitcoin](https://en.cryptobadges.io/badge/micro/1F29aNKQzci3ga5LDcHHawYzFPXvELTFoL)](https://en.cryptobadges.io/donate/1F29aNKQzci3ga5LDcHHawYzFPXvELTFoL) [![Donate with Ethereum](https://en.cryptobadges.io/badge/micro/0x0f58B66993a315dbCc102b4276298B5Ff8895F41)](https://en.cryptobadges.io/donate/0x0f58B66993a315dbCc102b4276298B5Ff8895F41)

A Bungee/Waterfall plugin that will block players from connecting with certain versions.
## Features
- Block players if they are using a version that is on the list.
- Reload using `/bbvreload`. (bbv.reload)

## Config
This is the config. You need to use the version number for a version. To find them, go [here](https://wiki.vg/Protocol_version_numbers).
```yaml
# These versions will NOT be allowed to connect.
# These versions MUST be the version number. You can check the numbers here: https://wiki.vg/Protocol_version_numbers
# By default, all versions are listed here.
versions:
  - 47
  - 107
  - 108
  - 109
  - 110
  - ...

# Send this message if someone connects with a blocked version.
disconnect-message: "&cYou cannot connect with this version!"
```
