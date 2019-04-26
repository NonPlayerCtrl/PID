TYPE
 UsbFilesArray : ARRAY  [1..10] OF STRING(255);
 tsUsbFile : 
   STRUCT 
    unnamedStructElem1 : INT;
    nPLC1 : STRING(255);
   END_STRUCT;
 UsbUse : 
   STRUCT 
    plc1 : BOOL;
    plc2 : BOOL;
    plc3 : BOOL;
    plc4 : BOOL;
    plc5 : BOOL;
    plc6 : BOOL;
    plc7 : BOOL;
    plc8 : BOOL;
    plc9 : BOOL;
    plc10 : BOOL;
   END_STRUCT;
 // tnUsbFile : (nPLC1, nPLC2, nPLC3, nPLC4, nPLC5, nPLC6, nPLC7, nPLC8, nPLC9, nPLC10);
END_TYPE



#END_OF_IEC_PART

@Puma @IecEditor 6 152 @Tou 25 
@@@BEG_Comment@@@

@@@END_Comment@@@

@BEG_Contents 

@BEG_Export 
@RT(22)TypeConstTreeContainer 
0 
@RT(17)TypeTreeContainer 
4 
@DT @RT(13)UsbFilesArray @RT(0) @T @T @ARRAY 0 @T @DT @RT(11)STRING(255) @RT(0) @T @T @STRING 0 @F @RT(3)255 @F 
@F 1 @RT(1)1 @RT(2)10 0 @F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 


@DT @RT(9)tsUsbFile @RT(0) @T @T @STRUCT 0 2 

@StructElem @RT(18)unnamedStructElem1 @RT(0) @T @F @DT @RT(3)INT @RT(0) @T @T @BASIC 0 @F 
@F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 

@StructElem @RT(5)nPLC1 @RT(0) @T @F @DT @RT(11)STRING(255) @RT(0) @T @T @STRING 0 @F @RT(3)255 @F 
@F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 
@F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 


@DT @RT(6)UsbUse @RT(0) @T @T @STRUCT 0 10 

@StructElem @RT(4)plc1 @RT(0) @T @F @DT @RT(4)BOOL @RT(0) @T @T @BASIC 0 @F 
@F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 

@StructElem @RT(4)plc2 @RT(0) @T @F @DT @RT(4)BOOL @RT(0) @T @T @BASIC 0 @F 
@F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 

@StructElem @RT(4)plc3 @RT(0) @T @F @DT @RT(4)BOOL @RT(0) @T @T @BASIC 0 @F 
@F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 

@StructElem @RT(4)plc4 @RT(0) @T @F @DT @RT(4)BOOL @RT(0) @T @T @BASIC 0 @F 
@F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 

@StructElem @RT(4)plc5 @RT(0) @T @F @DT @RT(4)BOOL @RT(0) @T @T @BASIC 0 @F 
@F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 

@StructElem @RT(4)plc6 @RT(0) @T @F @DT @RT(4)BOOL @RT(0) @T @T @BASIC 0 @F 
@F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 

@StructElem @RT(4)plc7 @RT(0) @T @F @DT @RT(4)BOOL @RT(0) @T @T @BASIC 0 @F 
@F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 

@StructElem @RT(4)plc8 @RT(0) @T @F @DT @RT(4)BOOL @RT(0) @T @T @BASIC 0 @F 
@F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 

@StructElem @RT(4)plc9 @RT(0) @T @F @DT @RT(4)BOOL @RT(0) @T @T @BASIC 0 @F 
@F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 

@StructElem @RT(5)plc10 @RT(0) @T @F @DT @RT(4)BOOL @RT(0) @T @T @BASIC 0 @F 
@F @F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 
@F 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 


@DT @RT(9)tnUsbFile @RT(0) @T @T @ENUM 0 10 
@EnumConst @RT(5)nPLC1 @RT(0) @F @T 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 
@EnumConst @RT(5)nPLC2 @RT(0) @F @T 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 
@EnumConst @RT(5)nPLC3 @RT(0) @F @T 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 
@EnumConst @RT(5)nPLC4 @RT(0) @F @T 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 
@EnumConst @RT(5)nPLC5 @RT(0) @F @T 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 
@EnumConst @RT(5)nPLC6 @RT(0) @F @T 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 
@EnumConst @RT(5)nPLC7 @RT(0) @F @T 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 
@EnumConst @RT(5)nPLC8 @RT(0) @F @T 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 
@EnumConst @RT(5)nPLC9 @RT(0) @F @T 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 
@EnumConst @RT(6)nPLC10 @RT(0) @F @T 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 
@T 
@T 
@BEG_Attrib 
2 @RT(0) @RT(0) @RT(0) 
@END_Attrib 


@END_Export 

@END_Contents 
