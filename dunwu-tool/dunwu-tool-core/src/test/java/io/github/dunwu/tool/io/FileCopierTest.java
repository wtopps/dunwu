package io.github.dunwu.tool.io;

import io.github.dunwu.tool.io.file.FileCopier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 文件拷贝单元测试
 *
 * @author Looly
 */
public class FileCopierTest {

    @Test
    @Disabled
    public void dirCopyTest() {
        FileCopier copier = FileCopier.create("D:\\Java", "e:/eclipse/eclipse2.zip");
        copier.copy();
    }

    @Test
    @Disabled
    public void dirCopyTest2() {
        //测试带.的文件夹复制
        FileCopier copier = FileCopier.create("D:\\workspace\\java\\.metadata", "D:\\workspace\\java\\.metadata\\temp");
        copier.copy();

        FileUtil.copy("D:\\workspace\\java\\looly\\hutool\\.git", "D:\\workspace\\java\\temp", true);
    }

    @Test
    public void dirCopySubTest() {
        //测试父目录复制到子目录报错
        FileCopier copier = FileCopier.create("D:\\workspace\\java\\.metadata", "D:\\workspace\\java\\.metadata\\temp");
        copier.copy();
    }

    @Test
    @Disabled
    public void copyFileToDirTest() {
        FileCopier copier = FileCopier.create("d:/GReen_Soft/XshellXftpPortable.zip", "c:/hp/");
        copier.copy();
    }

}
