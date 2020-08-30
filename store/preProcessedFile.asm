addi $4,$0,400
addi $5,$0,400
trap 150
main:
addi $4,$0,10
trap 106
addi $30,$30,1
trap 111
addi $23,$0,16
addi $4,$0,0
rotateLoop:
add $5,$0,$30
jal rotateAndSavePoint
trap 111
addi $4,$4,4
bne $4,$23,rotateLoop
drawLoop:
addi $4,$0,0
addi $4,$4,2
lh $5,rotatedObjectPoints($4)
addi $4,$4,-2
lh $4,rotatedObjectPoints($4)
addi $6,$0,200
addi $7,$0,200
jal drawLine
trap 153
add $4,$0,$0
trap 156
j main
trap 0
rotateAndSavePoint:
lh $8,objectPoints($4)
addi $4,$4,2
lh $9,objectPoints($4)
addi $4,$4,-2
addi $1,$5,256
andi $1,$1,0x3FF
sll $1,$1,2
lw $1,sinTable($1)
multu $1,$8
mfhi $2
addi $2,$2,1
sra $1,$8,1
sub $2,$2,$1
add $10,$0,$2
andi $1,$5,0x3FF
sll $1,$1,2
lw $1,sinTable($1)
multu $1,$9
mfhi $2
addi $2,$2,1
sra $1,$9,1
sub $2,$2,$1
add $11,$0,$2
andi $1,$5,0x3FF
sll $1,$1,2
lw $1,sinTable($1)
multu $1,$8
mfhi $2
addi $2,$2,1
sra $1,$8,1
sub $2,$2,$1
add $6,$0,$2
addi $1,$5,256
andi $1,$1,0x3FF
sll $1,$1,2
lw $1,sinTable($1)
multu $1,$9
mfhi $2
addi $2,$2,1
sra $1,$9,1
sub $2,$2,$1
add $7,$0,$2
sub $10,$10,$11
add $11,$6,$7
addi $10,$10,200
addi $11,$11,200
sh $10,rotatedObjectPoints($4)
addi $4,$4,2
sh $11,rotatedObjectPoints($4)
addi $4,$4,-2
jr $31
drawLine:
add $24,$0,$0
add $16,$0,$4
add $17,$0,$5
add $18,$0,$6
add $19,$0,$7
sub $8,$6,$4
bgtz $8,dxABSEnd
nor $8,$8,$0
addi $8,$8,1
dxABSEnd:
sub $9,$7,$5
bgtz $9,dyABSEnd
nor $9,$9,$0
addi $9,$9,1
dyABSEnd:
add $10,$8,$8
add $11,$9,$9
slt $1,$4,$6
bgtz $1,2
addi $12,$0,-1
j 1
addi $12,$0,1
slt $1,$5,$7
bgtz $1,2
addi $13,$0,-1
j 1
addi $13,$0,1
add $14,$0,$4
add $15,$0,$5
slt $1,$8,$9
bgtz $1,lineDrawSegIf
drawLineLoop1:
add $4,$0,$14
add $5,$0,$15
lw $6,color($0)
trap 151
add $4,$0,$16
add $5,$0,$17
add $6,$0,$18
beq $14,$6,endDrawLine
add $14,$14,$12
add $24,$24,$11
slt $1,$8,$24
nor $1,$1,$0
bgtz $1,2
add $15,$15,$13
sub $24,$24,$10
j drawLineLoop1
lineDrawSegIf:
drawLineLoop2:
add $4,$0,$14
add $5,$0,$15
lw $6,color($0)
trap 151
add $4,$0,$16
add $5,$0,$17
add $6,$0,$18
beq $15,$7,endDrawLine
add $15,$15,$13
add $24,$24,$10
slt $1,$9,$24
nor $1,$1,$0
bgtz $1,2
add $14,$14,$12
sub $24,$24,$11
j drawLineLoop2
endDrawLine:
jr $31
objectPoints:
.hword 0,300
.hword 300,300
.hword 300,0
.hword 0,0
rotatedObjectPoints:
.space 4
objectLines:
.hword 0,1
.hword 1,2
.hword 2,3
.hword 3,0
color:
.word 0xFF0000
sinTable:
.word 0x80000000,0x80c941d8,0x819281be,0x825bbdc2,0x8324f3f1,0x83ee225a,0x84b7470c,0x85806015,0x86496b84,0x87126768,0x87db51d1,0x88a428cd,0x896cea6c,0x8a3594be,0x8afe25d3,0x8bc69bba,0x8c8ef485,0x8d572e43,0x8e1f4707,0x8ee73ce1,0x8faf0de2,0x9076b81e,0x913e39a6,0x9205908d,0x92ccbae6,0x9393b6c5,0x945a823e,0x95211b65,0x95e78050,0x96adaf14,0x9773a5c7,0x98396280,0x98fee356,0x99c42660,0x9a8929b7,0x9b4deb73,0x9c1269b0,0x9cd6a285,0x9d9a9410,0x9e5e3c6a,0x9f2199b1,0x9fe4aa01,0xa0a76b78,0xa169dc35,0xa22bfa57,0xa2edc3fe,0xa3af374b,0xa4705260,0xa531135f,0xa5f1786c,0xa6b17fab,0xa7712741,0xa8306d56,0xa8ef500e,0xa9adcd94,0xaa6be410,0xab2991ab,0xabe6d492,0xaca3aaf0,0xad6012f2,0xae1c0ac6,0xaed7909d,0xaf92a2a5,0xb04d3f12,0xb1076415,0xb1c10fe2,0xb27a40ae,0xb332f4af,0xb3eb2a1d,0xb4a2df31,0xb55a1224,0xb610c131,0xb6c6ea94,0xb77c8c8c,0xb831a557,0xb8e63336,0xb99a346a,0xba4da735,0xbb0089de,0xbbb2daa9,0xbc6497dd,0xbd15bfc3,0xbdc650a5,0xbe7648cf,0xbf25a68d,0xbfd4682e,0xc0828c02,0xc130105b,0xc1dcf38b,0xc28933e7,0xc334cfc5,0xc3dfc57d,0xc48a1369,0xc533b7e2,0xc5dcb147,0xc684fdf4,0xc72c9c4b,0xc7d38aac,0xc879c77c,0xc91f511e,0xc9c425fa,0xca684478,0xcb0bab03,0xcbae5806,0xcc5049f0,0xccf17f2f,0xcd91f636,0xce31ad78,0xced0a36a,0xcf6ed683,0xd00c453b,0xd0a8ee0e,0xd144cf79,0xd1dfe7f9,0xd27a3610,0xd313b840,0xd3ac6d0d,0xd44452ff,0xd4db689d,0xd571ac72,0xd6071d0a,0xd69bb8f4,0xd72f7ec1,0xd7c26d04,0xd8548250,0xd8e5bd3d,0xd9761c65,0xda059e61
.word 0xda9441cf,0xdb22054f,0xdbaee782,0xdc3ae70c,0xdcc60292,0xdd5038bd,0xddd98837,0xde61efad,0xdee96dcd,0xdf700149,0xdff5a8d3,0xe07a6322,0xe0fe2eed,0xe1810aee,0xe202f5e2,0xe283ee87,0xe303f3a0,0xe38303ee,0xe4011e39,0xe47e4148,0xe4fa6be7,0xe5759ce1,0xe5efd307,0xe6690d2a,0xe6e14a1f,0xe75888bd,0xe7cec7dc,0xe8440658,0xe8b84310,0xe92b7ce4,0xe99db2b7,0xea0ee36f,0xea7f0df4,0xeaee3131,0xeb5c4c13,0xebc95d89,0xec356487,0xeca06001,0xed0a4eee,0xed73304a,0xeddb030f,0xee41c63e,0xeea778d9,0xef0c19e4,0xef6fa867,0xefd2236b,0xf03389fd,0xf093db2c,0xf0f3160a,0xf15139ac,0xf1ae4529,0xf20a379a,0xf265101d,0xf2becdd0,0xf3176fd7,0xf36ef555,0xf3c55d74,0xf41aa75c,0xf46ed23b,0xf4c1dd41,0xf513c7a1,0xf5649090,0xf5b43746,0xf602baff,0xf6501af9,0xf69c5673,0xf6e76cb3,0xf7315cfd,0xf77a269c,0xf7c1c8db,0xf8084309,0xf84d9478,0xf891bc7d,0xf8d4ba6f,0xf9168da8,0xf9573586,0xf996b169,0xf9d500b4,0xfa1222cd,0xfa4e171d,0xfa88dd10,0xfac27413,0xfafadb9a,0xfb321318,0xfb681a05,0xfb9cefdb,0xfbd09419,0xfc03063d,0xfc3445cc,0xfc64524b,0xfc932b44,0xfcc0d043,0xfced40d8,0xfd187c93,0xfd42830b,0xfd6b53d8,0xfd92ee94,0xfdb952de,0xfdde8057,0xfe0276a3,0xfe253569,0xfe46bc53,0xfe670b0e,0xfe86214a,0xfea3febb,0xfec0a316,0xfedc0e16,0xfef63f75,0xff0f36f4,0xff26f454,0xff3d775c,0xff52bfd2,0xff66cd83,0xff79a03d,0xff8b37d2,0xff9b9416,0xffaab4e0,0xffb89a0b,0xffc54376,0xffd0b0ff,0xffdae28c,0xffe3d804,0xffeb914f,0xfff20e5a,0xfff74f17,0xfffb5377,0xfffe1b72,0xffffa6ff
.word 0xfffff61b,0xffff08c6,0xfffcdf02,0xfff978d3,0xfff4d643,0xffeef75d,0xffe7dc30,0xffdf84cc,0xffd5f147,0xffcb21b8,0xffbf163b,0xffb1ceec,0xffa34bec,0xff938d61,0xff82936f,0xff705e42,0xff5cee07,0xff4842ec,0xff325d27,0xff1b3cec,0xff02e275,0xfee94dfe,0xfece7fc6,0xfeb27810,0xfe953721,0xfe76bd41,0xfe570abb,0xfe361fde,0xfe13fcfc,0xfdf0a268,0xfdcc107b,0xfda6478e,0xfd7f47fe,0xfd57122e,0xfd2da67e,0xfd030557,0xfcd72f22,0xfcaa244a,0xfc7be540,0xfc4c7275,0xfc1bcc5f,0xfbe9f376,0xfbb6e835,0xfb82ab1b,0xfb4d3ca9,0xfb169d63,0xfadecdcf,0xfaa5ce78,0xfa6b9feb,0xfa3042b8,0xf9f3b771,0xf9b5feac,0xf9771901,0xf937070d,0xf8f5c96d,0xf8b360c2,0xf86fcdb2,0xf82b10e3,0xf7e52aff,0xf79e1cb2,0xf755e6ad,0xf70c89a2,0xf6c20646,0xf6765d52,0xf6298f80,0xf5db9d8f,0xf58c883e,0xf53c5053,0xf4eaf692,0xf4987bc6,0xf444e0b9,0xf3f0263c,0xf39a4d1e,0xf3435635,0xf2eb4257,0xf292125e,0xf237c727,0xf1dc6191,0xf17fe27d,0xf1224ad1,0xf0c39b74,0xf063d550,0xf002f952,0xefa10868,0xef3e0386,0xeed9eba0,0xee74c1ae,0xee0e86a9,0xeda73b8f,0xed3ee15f,0xecd5791a,0xec6b03c5,0xebff8268,0xeb92f60c,0xeb255fbe,0xeab6c08d,0xea471989,0xe9d66bc8,0xe964b85f,0xe8f20068,0xe87e44ff,0xe8098740,0xe793c84e,0xe71d094b,0xe6a54b5d,0xe62c8fac,0xe5b2d761,0xe53823ab,0xe4bc75b9,0xe43fcebc,0xe3c22fe8,0xe3439a74,0xe2c40f99,0xe2439092,0xe1c21e9d,0xe13fbafb,0xe0bc66ec,0xe03823b6,0xdfb2f2a1,0xdf2cd4f4,0xdea5cbfd,0xde1dd908,0xdd94fd65,0xdd0b3a68,0xdc809165,0xdbf503b2,0xdb6892a8,0xdadb3fa3
.word 0xda4d0c00,0xd9bdf91e,0xd92e0860,0xd89d3b29,0xd80b92df,0xd77910eb,0xd6e5b6b5,0xd65185ac,0xd5bc7f3c,0xd526a4d8,0xd48ff7f0,0xd3f879f9,0xd3602c6b,0xd2c710bd,0xd22d286a,0xd19274ee,0xd0f6f7c9,0xd05ab27a,0xcfbda683,0xcf1fd56a,0xce8140b4,0xcde1e9e9,0xcd41d293,0xcca0fc3e,0xcbff6877,0xcb5d18ce,0xcaba0ed5,0xca164c1e,0xc971d23e,0xc8cca2cc,0xc826bf60,0xc7802994,0xc6d8e304,0xc630ed4e,0xc5884a11,0xc4defaee,0xc4350187,0xc38a5f80,0xc2df1680,0xc233282e,0xc1869633,0xc0d96239,0xc02b8ded,0xbf7d1afc,0xbece0b16,0xbe1e5fec,0xbd6e1b30,0xbcbd3e95,0xbc0bcbd1,0xbb59c49b,0xbaa72aaa,0xb9f3ffb8,0xb9404581,0xb88bfdc0,0xb7d72a33,0xb721cc9a,0xb66be6b4,0xb5b57a43,0xb4fe890b,0xb44714cf,0xb38f1f55,0xb2d6aa64,0xb21db7c4,0xb164493e,0xb0aa609d,0xafefffac,0xaf352838,0xae79dc0e,0xadbe1cff,0xad01ecd9,0xac454d6f,0xab884093,0xaacac817,0xaa0ce5d1,0xa94e9b95,0xa88feb3b,0xa7d0d699,0xa7115f89,0xa65187e3,0xa5915181,0xa4d0be40,0xa40fcffa,0xa34e888e,0xa28ce9d8,0xa1caf5b7,0xa108ae0c,0xa04614b6,0x9f832b96,0x9ebff48e,0x9dfc7181,0x9d38a452,0x9c748ee6,0x9bb03320,0x9aeb92e7,0x9a26b020,0x99618cb3,0x989c2a86,0x97d68b82,0x9710b18e,0x964a9e96,0x95845481,0x94bdd53a,0x93f722ad,0x93303ec3,0x92692b6a,0x91a1ea8c,0x90da7e17,0x9012e7f8,0x8f4b2a1b,0x8e834670,0x8dbb3ee4,0x8cf31565,0x8c2acbe2,0x8b62644b,0x8a99e08f,0x89d1429e,0x89088c67,0x883fbfdb,0x8776deeb,0x86adeb86,0x85e4e79f,0x851bd524,0x8452b609,0x83898c3d,0x82c059b3,0x81f7205c,0x812de228,0x8064a10b
.word 0x7f9b5ef4,0x7ed21dd7,0x7e08dfa3,0x7d3fa64c,0x7c7673c2,0x7bad49f6,0x7ae42adb,0x7a1b1860,0x79521479,0x78892114,0x77c04024,0x76f77398,0x762ebd61,0x75661f70,0x749d9bb4,0x73d5341d,0x730cea9a,0x7244c11b,0x717cb98f,0x70b4d5e4,0x6fed1807,0x6f2581e8,0x6e5e1573,0x6d96d495,0x6ccfc13c,0x6c08dd52,0x6b422ac5,0x6a7bab7e,0x69b56169,0x68ef4e71,0x6829747d,0x6763d579,0x669e734c,0x65d94fdf,0x65146d18,0x644fccdf,0x638b7119,0x62c75bad,0x62038e7e,0x61400b71,0x607cd469,0x5fb9eb49,0x5ef751f3,0x5e350a48,0x5d731627,0x5cb17771,0x5bf03005,0x5b2f41bf,0x5a6eae7e,0x59ae781c,0x58eea076,0x582f2966,0x577014c4,0x56b1646a,0x55f31a2e,0x553537e8,0x5477bf6c,0x53bab290,0x52fe1326,0x5241e300,0x518623f1,0x50cad7c7,0x50100053,0x4f559f62,0x4e9bb6c1,0x4de2483b,0x4d29559b,0x4c70e0aa,0x4bb8eb30,0x4b0176f4,0x4a4a85bc,0x4994194b,0x48de3365,0x4828d5cc,0x4774023f,0x46bfba7e,0x460c0047,0x4558d555,0x44a63b64,0x43f4342e,0x4342c16a,0x4291e4cf,0x41e1a013,0x4131f4e9,0x4082e503,0x3fd47212,0x3f269dc6,0x3e7969cc,0x3dccd7d1,0x3d20e97f,0x3c75a07f,0x3bcafe78,0x3b210511,0x3a77b5ee,0x39cf12b1,0x39271cfb,0x387fd66b,0x37d9409f,0x37335d33,0x368e2dc1,0x35e9b3e1,0x3545f12a,0x34a2e731,0x34009788,0x335f03c1,0x32be2d6c,0x321e1616,0x317ebf4b,0x30e02a95,0x3042597c,0x2fa54d85,0x2f090836,0x2e6d8b11,0x2dd2d795,0x2d38ef42,0x2c9fd394,0x2c078606,0x2b70080f,0x2ad95b27,0x2a4380c3,0x29ae7a53,0x291a494a,0x2886ef14,0x27f46d20,0x2762c4d6,0x26d1f79f,0x264206e1,0x25b2f3ff
.word 0x2524c05c,0x24976d57,0x240afc4d,0x237f6e9a,0x22f4c597,0x226b029a,0x21e226f7,0x215a3402,0x20d32b0b,0x204d0d5e,0x1fc7dc49,0x1f439913,0x1ec04504,0x1e3de162,0x1dbc6f6d,0x1d3bf066,0x1cbc658b,0x1c3dd017,0x1bc03143,0x1b438a46,0x1ac7dc54,0x1a4d289e,0x19d37053,0x195ab4a2,0x18e2f6b4,0x186c37b1,0x17f678bf,0x1781bb00,0x170dff97,0x169b47a0,0x16299437,0x15b8e676,0x15493f72,0x14daa041,0x146d09f3,0x14007d97,0x1394fc3a,0x132a86e5,0x12c11ea0,0x1258c470,0x11f17956,0x118b3e51,0x1126145f,0x10c1fc79,0x105ef797,0xffd06ad,0xf9c2aaf,0xf3c648b,0xeddb52e,0xe801d82,0xe239e6e,0xdc838d8,0xd6deda1,0xd14bda8,0xcbca9ca,0xc65b2e1,0xc0fd9c3,0xbbb1f46,0xb678439,0xb15096d,0xac3afac,0xa7377c1,0xa246270,0x9d6707f,0x989a2ad,0x93df9b9,0x8f3765d,0x8aa1952,0x861e34d,0x81ad500,0x7d4ef1c,0x790324d,0x74c9f3d,0x70a3692,0x6c8f8f2,0x688e6fe,0x64a0153,0x60c488e,0x5cfbd47,0x5946014,0x55a3187,0x5213230,0x4e9629c,0x4b2c356,0x47d54e4,0x44917ca,0x4160c89,0x3e433a0,0x3b38d8a,0x3841abf,0x355dbb5,0x328d0dd,0x2fcfaa8,0x2d25981,0x2a8edd1,0x280b801,0x259b871,0x233ef84,0x20f5d97,0x1ec0303,0x1c9e021,0x1a8f544,0x18942be,0x16ac8de,0x14d87ef,0x1318039,0x116b201,0xfd1d8a,0xe4c313,0xcda2d8,0xb7bd13,0xa311f8,0x8fa1bd,0x7d6c90,0x6c729e,0x5cb413,0x4e3113,0x40e9c4,0x34de47,0x2a0eb8,0x207b33,0x1823cf,0x1108a2,0xb29bc,0x6872c,0x320fd,0xf739,0x9e4
.word 0x5900,0x1e48d,0x4ac88,0x8b0e8,0xdf1a5,0x146eb0,0x1c27fb,0x251d73,0x2f4f00,0x3abc89,0x4765f4,0x554b1f,0x646be9,0x74c82d,0x865fc2,0x99327c,0xad402d,0xc288a3,0xd90bab,0xf0c90b,0x109c08a,0x123f1e9,0x13f5ce9,0x15c0144,0x179deb5,0x198f4f1,0x1b943ac,0x1daca96,0x1fd895c,0x2217fa8,0x246ad21,0x26d116b,0x294ac27,0x2bd7cf4,0x2e7836c,0x312bf27,0x33f2fbc,0x36cd4bb,0x39badb4,0x3cbba33,0x3fcf9c2,0x42f6be6,0x4631024,0x497e5fa,0x4cdece7,0x5052465,0x53d8bec,0x57722ef,0x5b1e8e2,0x5eddd32,0x62aff4b,0x6694e96,0x6a8ca79,0x6e97257,0x72b4590,0x76e4382,0x7b26b87,0x7f7bcf6,0x83e3724,0x885d963,0x8cea302,0x918934c,0x963a98c,0x9afe506,0x9fd4500,0xa4bc8b9,0xa9b6f6f,0xaec385e,0xb3e22be,0xb912dc4,0xbe558a3,0xc3aa28b,0xc910aaa,0xce89028,0xd41322f,0xd9aefe2,0xdf5c865,0xe51bad6,0xeaec653,0xf0ce9f5,0xf6c24d3,0xfcc7602,0x102ddc94,0x10905798,0x10f3e61b,0x11588726,0x11be39c1,0x1224fcf0,0x128ccfb5,0x12f5b111,0x135f9ffe,0x13ca9b78,0x1436a276,0x14a3b3ec,0x1511cece,0x1580f20b,0x15f11c90,0x16624d48,0x16d4831b,0x1747bcef,0x17bbf9a7,0x18313823,0x18a77742,0x191eb5e0,0x1996f2d5,0x1a102cf8,0x1a8a631e,0x1b059418,0x1b81beb7,0x1bfee1c6,0x1c7cfc11,0x1cfc0c5f,0x1d7c1178,0x1dfd0a1d,0x1e7ef511,0x1f01d112,0x1f859cdd,0x200a572c,0x208ffeb6,0x21169232,0x219e1052,0x222677c8,0x22afc742,0x2339fd6d,0x23c518f3,0x2451187d,0x24ddfab0,0x256bbe30
.word 0x25fa619e,0x2689e39a,0x271a42c2,0x27ab7daf,0x283d92fb,0x28d0813e,0x2964470b,0x29f8e2f5,0x2a8e538d,0x2b249762,0x2bbbad00,0x2c5392f2,0x2cec47bf,0x2d85c9ef,0x2e201806,0x2ebb3086,0x2f5711f1,0x2ff3bac4,0x3091297c,0x312f5c95,0x31ce5287,0x326e09c9,0x330e80d0,0x33afb60f,0x3451a7f9,0x34f454fc,0x3597bb87,0x363bda05,0x36e0aee1,0x37863883,0x382c7553,0x38d363b4,0x397b020b,0x3a234eb8,0x3acc481d,0x3b75ec96,0x3c203a82,0x3ccb303a,0x3d76cc18,0x3e230c74,0x3ecfefa4,0x3f7d73fd,0x402b97d1,0x40da5972,0x4189b730,0x4239af5a,0x42ea403c,0x439b6822,0x444d2556,0x44ff7621,0x45b258ca,0x4665cb95,0x4719ccc9,0x47ce5aa8,0x48837373,0x4939156b,0x49ef3ece,0x4aa5eddb,0x4b5d20ce,0x4c14d5e2,0x4ccd0b50,0x4d85bf51,0x4e3ef01d,0x4ef89bea,0x4fb2c0ed,0x506d5d5a,0x51286f62,0x51e3f539,0x529fed0d,0x535c550f,0x54192b6d,0x54d66e54,0x55941bef,0x5652326b,0x5710aff1,0x57cf92a9,0x588ed8be,0x594e8054,0x5a0e8793,0x5aceeca0,0x5b8fad9f,0x5c50c8b4,0x5d123c01,0x5dd405a8,0x5e9623ca,0x5f589487,0x601b55fe,0x60de664e,0x61a1c395,0x62656bef,0x63295d7a,0x63ed964f,0x64b2148c,0x6576d648,0x663bd99f,0x67011ca9,0x67c69d7f,0x688c5a38,0x695250eb,0x6a187faf,0x6adee49a,0x6ba57dc1,0x6c6c493a,0x6d334519,0x6dfa6f72,0x6ec1c659,0x6f8947e1,0x7050f21d,0x7118c31e,0x71e0b8f8,0x72a8d1bc,0x73710b7a,0x74396445,0x7501da2c,0x75ca6b41,0x76931593,0x775bd732,0x7824ae2e,0x78ed9897,0x79b6947b,0x7a7f9fea,0x7b48b8f3,0x7c11dda5,0x7cdb0c0e,0x7da4423d,0x7e6d7e41,0x7f36be27,0x80000000
