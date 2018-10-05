# Apt-Tutorials

我在做 [基于 GitHub App 业务深度讲解 Kotlin1.2高级特性与框架设计](https://coding.imooc.com/class/232.html) 这门课的时候，顺便做了一个注解处理器的框架，叫 [Tieguanyin(铁观音)](https://github.com/enbandari/TieGuanYin)，这个框架主要是用来解决 Activity 跳转时传参的问题，我们知道 Activity 如果需要参数，那么我们只能非常繁琐的使用 `Intent` 来传递，有了这个框架我们就可以省去这个麻烦的步骤。

在这里，框架的内容其实不是重点，重点是，它是一个注解处理器的项目。为了让它的作用尽可能的放大，我对原框架做了简化，做了这套课程。

# 视频源码

见 Github 项目：[Apt-Tutorials](https://github.com/enbandari/Apt-Tutorials)

# 视频观看

## 1. 课程简介

作为课程的第一节，我们简单介绍了一下 Apt 的工作原理和流程，并对整个视频课程涉及的内容做了介绍。

**腾讯视频：** https://v.qq.com/x/page/l0728uxh61x.html

**Bilibili：** https://www.bilibili.com/video/av32905508/?p=1

## 2. 搭建工程

这一节主要介绍注解处理器工程的结构。

**腾讯视频：** https://v.qq.com/x/page/o07284u5rju.html


**Bilibili：** https://www.bilibili.com/video/av32905508/?p=2

## 3. 解析注解

这一节主要介绍如何解析注解获取被标注的元素信息。

**腾讯视频：** https://v.qq.com/x/page/z07286r97mz.html

**Bilibili：** https://www.bilibili.com/video/av32905508/?p=3

## 4. 生成常量

这一节为被标注的属性生成一些常量字符串来作为它们的 Key，也展示了如何为生成的 Java 类添加常量。

**腾讯视频：** https://v.qq.com/x/page/k0728tn7sty.html

**Bilibili：** https://www.bilibili.com/video/av32905508/?p=4

## 5. 生成 start 方法

这一节为被标注的 Activity 生成携带被标注的属性作为参数的 start 的方法，也展示了如何为生成的 Java 类添加方法。

**腾讯视频：** https://v.qq.com/x/page/x0728j7j7a6.html

**Bilibili：** https://www.bilibili.com/video/av32905508/?p=5

## 6. 生成注入和状态保存的方法

这一节为被标注的 Activity 生成注入属性的 inject 方法和保存状态的 saveState 方法。

**腾讯视频：** https://v.qq.com/x/page/d0728uitqdr.html

**Bilibili：** https://www.bilibili.com/video/av32905508/?p=6

## 7. 生成 Kotlin 代码

这一节主要通过为被标注的 Activity 生成扩展方法来介绍如何生成 Kotlin 代码。

**腾讯视频：** https://v.qq.com/x/page/l0728y2inrf.html

**Bilibili：** https://www.bilibili.com/video/av32905508/?p=7

## 8. 小结

这一节对整个课程作了一个总结。

**腾讯视频：** https://v.qq.com/x/page/m072848g85p.html

**Bilibili：** https://www.bilibili.com/video/av32905508/?p=8

---

欢迎关注 Kotlin 公众号：

![Kotlin 公众号](arts/Kotlin.jpg)



# [License](LICENSE)

> MIT License
> 
> Copyright (c) 2018 Bennyhuo
> 
> Permission is hereby granted, free of charge, to any person obtaining a copy
> of this software and associated documentation files (the "Software"), to deal
> in the Software without restriction, including without limitation the rights
> to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
> copies of the Software, and to permit persons to whom the Software is
> furnished to do so, subject to the following conditions:
> 
> The above copyright notice and this permission notice shall be included in all
> copies or substantial portions of the Software.
> 
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
> IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
> FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
> AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
> LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
> OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
> SOFTWARE.
