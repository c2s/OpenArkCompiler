/*
 * Copyright (c) [2020] Huawei Technologies Co.,Ltd.All rights reserved.
 *
 * OpenArkCompiler is licensed under the Mulan PSL v1.
 * You can use this software according to the terms and conditions of the Mulan PSL v1.
 * You may obtain a copy of Mulan PSL v1 at:
 *
 *     http://license.coscl.org.cn/MulanPSL
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR
 * FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v1 for more details.
 * -@TestCaseID: UnsafeputCharTest
 * -@TestCaseName: Unsafe api: putChar()
 * -@TestCaseType: Function Test
 * -@RequirementName: VMRuntime_registerNativeAllocation接口实现
 * -@Brief:
 * -#step1:prepare one Class and get Field of Char
 * -#step2:invoke Unsafe.getChar to visit this Field
 * -#step3:set value of step2 by Unsafe.putChar
 * -#step4:check value of step3 correct
 * -@Expect:0\n
 * -@Priority: High
 * -@Source: UnsafeputCharTest.java
 * -@ExecuteClass: UnsafeputCharTest
 * -@ExecuteArgs:
 */
import sun.misc.Unsafe;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeputCharTest {
    private static int res = 99;
    public static void main(String[] args) {
        System.out.println(run(args, System.out));
    }

    private static int run(String[] args, PrintStream out) {
        int result = 2/*STATUS_FAILED*/;
        try {
            result = UnsafeputCharTest_1();
        } catch (Exception e) {
            e.printStackTrace();
            UnsafeputCharTest.res -= 2;
        }

        if (result == 3 && UnsafeputCharTest.res == 97) {
            result =0;
        }
        return result;
    }

    private static int UnsafeputCharTest_1() {
        Unsafe unsafe;
        Field field;
        Long offset;
        Field param;
        Object obj;
        char result;
        try {
            field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
            param = Billie26.class.getDeclaredField("favorite");
            offset = unsafe.objectFieldOffset(param);
            obj = new Billie26();
            result = unsafe.getChar(obj, offset);
            unsafe.putChar(obj, offset, 's');
            result = unsafe.getChar(obj, offset);
            if (result == 's') {
                UnsafeputCharTest.res -= 2;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return 40;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 41;
        }
        return 3;
    }
}

class Billie26 {
    public int height = 8;
    private String[] color = {"black","white"};
    private String owner = "Me";
    private byte length = 0x7;
    private String[] water = {"day","wet"};
    private long weight = 100L;
    private volatile int age = 18;
    private volatile long birth = 20010214L;
    private volatile String lastname = "eilish";
    private boolean gender  = false;
    private char favorite = '7';
    private short date = 6;
    private double grade = 99.99;
    private float aFloat = 66.66f;
}

// EXEC:%maple  %f %build_option -o %n.so
// EXEC:%run %n.so %n %run_option | compare %f
// ASSERT: scan 0\n