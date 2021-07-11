package com.kwok.util.commons;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * From org.nutz:nutz:org.nutz.lang.Stopwatch
 * 
 */
public class StopWatch {

	private boolean nano;

	private long from;

	private long to;

	private List<StopTag> tags;

	private StopTag lastTag;

	/**
	 * 秒表开始计时，计时时间的最小单位是毫秒
	 * 
	 * @return 开始计时的秒表对象
	 */
	public static StopWatch begin() {
		StopWatch sw = new StopWatch();
		sw.start();
		return sw;
	}

	/**
	 * 秒表开始计时，计时时间的最小单位是毫微秒
	 * 
	 * @return 开始计时的秒表对象
	 */
	public static StopWatch beginNano() {
		StopWatch sw = new StopWatch();
		sw.nano = true;
		sw.start();
		return sw;
	}

	/**
	 * 创建一个秒表对象，该对象的计时时间的最小单位是毫秒
	 * 
	 * @return 秒表对象
	 */
	public static StopWatch create() {
		return new StopWatch();
	}

	/**
	 * 创建一个秒表对象，该对象的计时时间的最小单位是毫微秒
	 * 
	 * @return 秒表对象
	 */
	public static StopWatch createNano() {
		StopWatch sw = new StopWatch();
		sw.nano = true;
		return sw;
	}

	public static StopWatch run(Runnable atom) {
		StopWatch sw = begin();
		atom.run();
		sw.stop();
		return sw;
	}

	public static StopWatch runNano(Runnable atom) {
		StopWatch sw = beginNano();
		atom.run();
		sw.stop();
		return sw;
	}

	/**
	 * 开始计时，并返回开始计时的时间，该时间最小单位由创建秒表时确定
	 * 
	 * @return 开始计时的时间
	 */
	public long start() {
		from = currentTime();
		to = from;
		return from;
	}

	private long currentTime() {
		return nano ? System.nanoTime() : System.currentTimeMillis();
	}

	/**
	 * 记录停止时间，该时间最小单位由创建秒表时确定
	 * 
	 * @return 自身以便链式赋值
	 */
	public long stop() {
		to = currentTime();
		return to;
	}

	/**
	 * @return 计时结果(ms)
	 */
	public long getDuration() {
		return to - from;
	}

	/**
	 * @see #getDuration()
	 */
	public long du() {
		return to - from;
	}

	/**
	 * 开始计时的时间，该时间最小单位由创建秒表时确定
	 * 
	 * @return 开始计时的时间
	 */
	public long getStartTime() {
		return from;
	}

	/**
	 * 停止计时的时间，该时间最小单位由创建秒表时确定
	 * 
	 * @return 停止计时的时间
	 */
	public long getEndTime() {
		return to;
	}

	/**
	 * 返回格式为 <code>Total: [计时时间][计时时间单位] : [计时开始时间]=>[计时结束时间]</code> 的字符串
	 * 
	 * @return 格式为 <code>Total: [计时时间][计时时间单位] : [计时开始时间]=>[计时结束时间]</code> 的字符串
	 */
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS");
		String prefix = String.format("Total: %d%s : [%s]=>[%s]", this.getDuration(), (nano ? "ns" : "ms"),
				sdf.format(new Date(from)), sdf.format(new Date(to)));
		if (tags == null)
			return prefix;
		StringBuilder sb = new StringBuilder(prefix).append("\r\n");
		for (int i = 0; i < tags.size(); i++) {
			StopTag tag = tags.get(i);
			sb.append(String.format("  -> %5s: %dms", tag.name == null ? "TAG" + i : tag.name, tag.du()));
			if (i < tags.size() - 1)
				sb.append("\r\n");
		}
		return sb.toString();
	}

	public StopTag tag(String name) {
		if (tags == null)
			tags = new LinkedList<StopWatch.StopTag>();
		lastTag = new StopTag(name, System.currentTimeMillis(), lastTag);
		tags.add(lastTag);
		return lastTag;
	}

	public StopTag tagf(String fmt, Object... args) {
		return tag(String.format(fmt, args));
	}

	public class StopTag {
		
		public String name;
		public long tm;
		public StopTag pre;

		public StopTag() {}

		public StopTag(String name, long tm, StopTag pre) {
			super();
			this.name = name;
			this.tm = tm;
			this.pre = pre;
		}

		public long du() {
			if (pre == null)
				return tm - from;
			return tm - pre.tm;
		}
	}

}