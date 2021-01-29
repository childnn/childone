package org.anonymous.netty.file.attribute;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.nio.file.attribute.FileAttribute
 * @see java.nio.file.attribute.AttributeView 文件属性的视图
 * -- XxxAttributes: 表示某种文件属性的集合. 可通过 XxxAttributeView 对象获取 XxxAttributes.
 * @see java.nio.file.attribute.AclFileAttributeView 为特定文件设置 ACL (access-control-list) 及文件所有者属性.
 * {@link java.nio.file.attribute.AclFileAttributeView#getAcl()} 返回 {@link java.nio.file.attribute.AclEntry} 集合,
 * 代表该文件的权限集; {@link java.nio.file.attribute.AclFileAttributeView#setAcl(java.util.List)} 修改该文件的 ACL.
 * @see java.nio.file.attribute.BasicFileAttributeView 可以获取或修改文件的基本属性, 包括文件的最后修改时间, 最后访问时间, 创建时间, 大小, 是否为目录等.
 * {@link java.nio.file.attribute.BasicFileAttributeView#readAttributes()} 方法返回 {@link java.nio.file.attribute.BasicFileAttributes}
 * 对文件夹基本属性的修改通过 {@link java.nio.file.attribute.BasicFileAttributes} 完成.
 * @see java.nio.file.attribute.DosFileAttributeView 用于获取或修改文件 DOS 相关属性, 比如文件是否只读, 是否隐藏, 是否为系统文件, 是否是存档文件等.
 * 它的 {@link java.nio.file.attribute.DosFileAttributeView#readAttributes()} 方法返回 {@link java.nio.file.attribute.DosFileAttributes}
 * 这些属性由该对象完成.
 * @see java.nio.file.attribute.FileOwnerAttributeView 获取/修改文件的所有者. {@link java.nio.file.attribute.FileOwnerAttributeView#getOwner()}
 * 返回 {@link java.nio.file.attribute.UserPrincipal} 代表文件的所有者. 也可以调用 {@link java.nio.file.attribute.FileOwnerAttributeView#setOwner(java.nio.file.attribute.UserPrincipal)}
 * 改变文件的所有者.
 * @see java.nio.file.attribute.PosixFileAttributeView 获取/修改 POSIX(portable operating system interface of UNIX)
 * {@link java.nio.file.attribute.PosixFileAttributeView#readAttributes()} 返回 {@link java.nio.file.attribute.PosixFileAttributes}
 * 该对象可用于获取或修改文件的所有者, 组所有者, 访问权限信息(也就是 UNIX 的 chmod 命令负责干的事情). 这个 View 直在 UNIX,Linux 等系统上可用.
 * @since 2021/1/22 19:05
 */
public class FileAttributeTest {

    public static void main(String[] args) throws IOException {
        final Path path = Paths.get("netty/src/main/java/org/anonymous/java7nio/file/attribute/FileAttributeTest.java");

        // basic
        final BasicFileAttributeView bav = Files.getFileAttributeView(path, BasicFileAttributeView.class);
        final BasicFileAttributes bfas = bav.readAttributes();
        final FileTime creationTime = bfas.creationTime();
        System.out.println("creationTime = " + creationTime);
        final FileTime lastAccessTime = bfas.lastAccessTime();
        System.out.println("lastAccessTime = " + lastAccessTime);
        final FileTime lastModifiedTime = bfas.lastModifiedTime();
        System.out.println("lastModifiedTime = " + lastModifiedTime);
        final long size = bfas.size();
        System.out.println("size = " + size);

        // owner
        final FileOwnerAttributeView foav = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
        final UserPrincipal owner = foav.getOwner();
        System.out.println("owner = " + owner);

        final UserPrincipal child = FileSystems.getDefault().getUserPrincipalLookupService().lookupPrincipalByName("Child");
        System.out.println("child = " + child);

        // UserDefinedFileAttributeView
        final UserDefinedFileAttributeView udfav = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
        final List<String> attrNames = udfav.list();
        attrNames.forEach(name -> {
            try {
                final ByteBuffer buf = ByteBuffer.allocate(udfav.size(name));
                udfav.read(name, buf);
                buf.flip();
                final CharBuffer cb = StandardCharsets.UTF_8.decode(buf);
                System.out.println(name + "---" + cb);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        final int write = udfav.write("自定义属性", StandardCharsets.UTF_8.encode("我是谁"));

        // DOS
        final DosFileAttributeView dfav = Files.getFileAttributeView(path, DosFileAttributeView.class);
        dfav.setHidden(true);
        dfav.setReadOnly(true);
    }

}
