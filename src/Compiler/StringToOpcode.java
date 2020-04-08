/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import java.nio.ByteBuffer;

/**
 *
 * @author parke
 */
public class StringToOpcode {
    
    private static int currentOpCodeLine;
    private static int currentByteIndex;

    public static byte[] stringToOpcode(CompileTimeUserLine ctul) {

        String instruction = ctul.line.line;
        currentOpCodeLine = ctul.line.realLineNumber;
        currentByteIndex = ctul.startingByteAddress;
        
        String opCodeString = "";
        String[] parameter = null;

        if (instruction == null || instruction.equals("")) {
            ASMCompiler.OpCodeError("instruction null", ctul.line.realLineNumber);
        } else {
            try {
                opCodeString = instruction.split(" ")[0];
                opCodeString = opCodeString.trim();
                parameter = instruction.substring(opCodeString.length()).split(",");
            } catch (Exception e) {
                 ASMCompiler.OpCodeError("Invalid parameters", ctul.line.realLineNumber);
                return new byte[0];
            }
        }

        switch (opCodeString) {

            //arithmetic and logical instructions
            case "add":
                return registerEncoding(0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[0]),
                        0, 0B100000);

            case "addu":
                return registerEncoding(0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[0]),
                        0, 0B100001);

            case "addi":
                return immediateEncoding(0B001000,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeImmediateValue(parameter[2]));

            case "addiu":
                return immediateEncoding(0B001001,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeImmediateValue(parameter[2]));

            case "and":
                return registerEncoding(0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[0]),
                        0, 0B100100);

            case "andi":
                return immediateEncoding(0B001100,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeImmediateValue(parameter[2]));

            case "div":
                return registerEncoding(0,
                        decodeRegister(parameter[0]),
                        decodeRegister(parameter[1]),
                        0, 0, 0B011010);

            case "divu":
                return registerEncoding(0,
                        decodeRegister(parameter[0]),
                        decodeRegister(parameter[1]),
                        0, 0, 0B011011);

            case "mult":
                return registerEncoding(0,
                        decodeRegister(parameter[0]),
                        decodeRegister(parameter[1]),
                        0, 0, 0B011000);

            case "multu":
                return registerEncoding(0,
                        decodeRegister(parameter[0]),
                        decodeRegister(parameter[1]),
                        0, 0, 0B11001);

            case "nor":
                return registerEncoding(0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[0]),
                        0, 0B100111);

            case "or":
                return registerEncoding(0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[0]),
                        0, 0B100101);

            case "ori":
                return immediateEncoding(0B001101,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeImmediateValue(parameter[2]));

            case "sll":
                return registerEncoding(0,
                        0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeImmediateValue(parameter[2]),
                        0B000000);
            case "sllv":
                return registerEncoding(0,
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        0, 0B000100);

            case "sra":
                return registerEncoding(0,
                        0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeImmediateValue(parameter[2]),
                        0B000011);

            case "srav":
                return registerEncoding(0,
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        0, 0B000111);

            case "srl":
                return registerEncoding(0,
                        0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeImmediateValue(parameter[2]),
                        0B000010);

            case "srlv":
                return registerEncoding(0,
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        0, 0B000110);

            case "sub":
                return registerEncoding(0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[0]),
                        0, 0B100010);

            case "subu":
                return registerEncoding(0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[0]),
                        0, 0B100011);

            case "xor":
                return registerEncoding(0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[0]),
                        0, 0B100110);

            case "xori":
                return immediateEncoding(0B001110,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeImmediateValue(parameter[2]));

            //constant manipulating instructions
            case "lhi":
                return immediateEncoding(0B011001,
                        decodeRegister(parameter[0]),
                        0,
                        decodeImmediateValue(parameter[1]));

            case "llo":
                return immediateEncoding(0B011000,
                        decodeRegister(parameter[0]),
                        0,
                        decodeImmediateValue(parameter[1]));

            //comparison instruction
            case "slt":
                return registerEncoding(0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[0]),
                        0, 0B101010);

            case "sltu":
                return registerEncoding(0,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[2]),
                        decodeRegister(parameter[0]),
                        0, 0B101001);

            case "slti":
                return immediateEncoding(0B001010,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeImmediateValue(parameter[2]));

            case "sltiu":
                return immediateEncoding(0B001001,
                        decodeRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeImmediateValue(parameter[2]));

            //branch instructions
            case "beq":
                return immediateEncoding(0B000100,
                        decodeRegister(parameter[0]),
                        decodeRegister(parameter[1]),
                        decodeMemoryPointerJump(parameter[2]));

            case "bgtz":
                return immediateEncoding(0B000111,
                        decodeRegister(parameter[0]),
                        0,
                        decodeMemoryPointerJump(parameter[1]));

            case "blez":
                return immediateEncoding(0B000110,
                        decodeRegister(parameter[0]),
                        0,
                        decodeMemoryPointerJump(parameter[1]));

            case "bne":
                return immediateEncoding(0B000101,
                        decodeRegister(parameter[0]),
                        decodeRegister(parameter[1]),
                        decodeMemoryPointerJump(parameter[2]));

            //jump instructions
            case "j":
                return jumpEncoding(0B000010,
                        decodeMemoryPointerJump(parameter[0]));

            case "jal":
                return jumpEncoding(0B000011,
                        decodeMemoryPointerJump(parameter[0]));

            case "jalr":
                return registerEncoding(0,
                        decodeRegister(parameter[0]),
                        0, 0, 0, 0B001001);

            case "jr":
                return registerEncoding(0,
                        decodeRegister(parameter[0]),
                        0, 0, 0, 0B001000);

            //load instruction
            case "lb":
                return immediateEncoding(0B100000,
                        decodeMemoryOpRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeMemoryOpPointerAddress(parameter[1]));

            case "lbu":
                return immediateEncoding(0B100100,
                        decodeMemoryOpRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeMemoryOpPointerAddress(parameter[1]));

            case "lh":
                return immediateEncoding(0B100001,
                        decodeMemoryOpRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeMemoryOpPointerAddress(parameter[1]));

            case "lhu":
                return immediateEncoding(0B100101,
                        decodeMemoryOpRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeMemoryOpPointerAddress(parameter[1]));

            case "lw":
                return immediateEncoding(0B100011,
                        decodeMemoryOpRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeMemoryOpPointerAddress(parameter[1]));

            //store instructions
            case "sb":
                return immediateEncoding(0B101000,
                        decodeMemoryOpRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeMemoryOpPointerAddress(parameter[1]));

            case "sh":
                return immediateEncoding(0B101001,
                        decodeMemoryOpRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeMemoryOpPointerAddress(parameter[1]));

            case "sw":
                return immediateEncoding(0B101011,
                        decodeMemoryOpRegister(parameter[1]),
                        decodeRegister(parameter[0]),
                        decodeMemoryOpPointerAddress(parameter[1]));

            //data movement instructions
            case "mfhi":
                return registerEncoding(0, 0, 0,
                        decodeRegister(parameter[0]),
                        0, 0B010000);

            case "mflo":
                return registerEncoding(0, 0, 0,
                        decodeRegister(parameter[0]),
                        0, 0B010010);

            case "mthi":
                return registerEncoding(0,
                        decodeRegister(parameter[0]),
                        0, 0, 0, 0B001001);

            case "mtlo":
                return registerEncoding(0,
                        decodeRegister(parameter[0]),
                        0, 0, 0, 0B001011);

            //system calls
            case "trap":
                return jumpEncoding(0B011010, decodeImmediateValue(parameter[0]));

            default:
                ASMCompiler.OpCodeError("Invalid opcode", ctul.line.realLineNumber);
                return new byte[0];
        }
        //return -1;
    }

    private static byte[] registerEncoding(int o, int s, int t, int d, int a, int f) {
        o = o & 0B111111;
        s = s & 0B11111;
        t = t & 0B11111;
        d = d & 0B11111;
        a = a & 0B11111;
        f = f & 0B111111;

        int temp =  (o << 26) | (s << 21) | (t << 16) | (d << 11) | (a << 6) | f;
        return ByteBuffer.allocate(4).putInt(temp).array();
    }

    private static byte[] immediateEncoding(int o, int s, int t, int i) {
        o = o & 0B111111;
        s = s & 0B11111;
        t = t & 0B11111;
        i = i & 0xFFFF;

        int temp = (o << 26) | (s << 21) | (t << 16) | i;
        return ByteBuffer.allocate(4).putInt(temp).array();

    }

    private static byte[] jumpEncoding(int o, int i) {
        o = o & 0B111111;
        i = i & 0x3FFFFFF;

        int temp =  (o << 26) | i;
        return ByteBuffer.allocate(4).putInt(temp).array();
    }

    public static int decodeRegister(String parameter) {
        if (!parameter.contains("$")) {
            ASMCompiler.ArgumentError("Invalid register",currentOpCodeLine);
            return 0;
        }
        try {
            return ASMCompiler.parseInt(parameter.replace("$", ""));
        } catch (Exception e) {
            ASMCompiler.ArgumentError("Invalid register number", currentOpCodeLine);
        }
        return 0;
    }

    private static int decodeImmediateValue(String parameter) {
        try {
            return ASMCompiler.parseInt(parameter);
        } catch (Exception e) {
            ASMCompiler.ArgumentError("Invalid immediate value", currentOpCodeLine);
        }
        return 0;
    }

    private static int decodeMemoryPointerJump(String parameter) {
        
        try{
            int temp = ASMCompiler.parseInt(parameter);
            return temp;
        }catch(Exception e){
            
        }
        return ((ASMCompiler.getByteIndexOfMemoryLable(parameter.trim(), currentOpCodeLine) - currentByteIndex) >> 2) - 1;
    }

    private static int decodeMemoryOpPointerAddress(String parameter) {
        try {
            return (ASMCompiler.getByteIndexOfMemoryLable((parameter.split("\\(")[0]), currentOpCodeLine));
        } catch (Exception e) {
            ASMCompiler.ArgumentError("Invalid Memory Pointer", currentOpCodeLine);
        }
        return 0;
    }

    private static int decodeMemoryOpRegister(String parameter) {
        try {
            return decodeRegister((parameter.split("\\(")[1].split("\\)")[0]));
        } catch (Exception e) {
            ASMCompiler.ArgumentError("Brackets not closed", currentOpCodeLine);
        }
        return 0;
    }
}
