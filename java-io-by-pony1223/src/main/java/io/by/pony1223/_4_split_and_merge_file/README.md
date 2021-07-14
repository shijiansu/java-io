# 实例：文件分割与合并

目的：将文件分割成数个部分，然后再将它们合并起来

首先文件的分割，有下面几个要点

1.先要确定的两个因素就是，分成多少块，每块多大，那么最后一块的大小不一定刚好能是你规定的每小块的大小，那么最后一块的大小就比较特殊，它等于文件总大小（块数-1）乘以每块大小

2.在操作源文件到目的文件，即被分割文件到分割文件时，实际上就是文件的拷贝过程

3.最关键的问题是如何控制文件输入流，它必须按照指定的位置读取每一个分块

比如，我有142k大小的文件，要将他们分割成3块，规定每块大小为50，那么我将第一块内容读取的时候，是从0-50K

当读第二快内容时就变成了50-100K，那么如何控制读取范围？

这里就需要用到RandomAccessFile类，它提供了一个seek方法，可以指定读取的开始位置

文件的合并就是将那些分块重新组合在一起，比文件分割考虑的因素少，这里提供了两种不同的方式进行文件合并，其实也就是关于输入流做出的改变，详细见下文，合并从小块文件到大文件，其实也就是文件追加输出