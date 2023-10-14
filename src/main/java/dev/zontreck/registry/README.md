Registry v3
==========

Registry v3 uses the same byte IDs and naming scheme as NBT. However, Registry includes a file header for some other
miscellaneous data.

Registry does technically include a method for saving as NBT and reading NBT. But, this is not the main priority. Ease
of use was the main goal here. If you wish to contribute to this project and add full NBT support, you may do so.

NOTE: Registry also introduces other tag types. Any tag type not found in the base NBT specification includes a
converter method. Please be sure to use those when utilizing NBT instead of Registry data.

Format Specification:

```
{ 'Total Length' 128 bytes
'Version' 1 byte
'Patch' 1 byte
'Format signature' variable bytes - UTF String
'Creator signature' variable bytes - UTF String prefixed with a 32-bit integer
'Timestamp' 8 bytes
'Number of tags' 4 bytes
}
```

https://github.com/zontreck/TP.Java.Registry