import java.util.NoSuchElementException;

/**
 * Class LinePoints provides an object that specifies a group of distinct
 * collinear points. To obtain the points:
 * <OL TYPE=1>
 * <P><LI>
 * Call the {@link #size() size()} method to obtain the number of points.
 * <P><LI>
 * Repeatedly call the {@link #hasNext() hasNext()} and {@link #next() next()}
 * methods to obtain the points themselves.
 * </OL>
 *
 * @author  Alan Kaminsky
 * @version 19-Oct-2018
 */
public class LinePoints
	implements PointSpec
	{

// Hidden data members.

	private int N;
	private Point p = new Point();
	private int i = 0;

// Exported constructors.

	/**
	 * Construct a new line points spec.
	 *
	 * @param  N  Number of points (&gt; 0).
	 *
	 * @exception  IllegalArgumentException
	 *     (unchecked exception) Thrown if <TT>N</TT> &le; 0.
	 */
	public LinePoints
		(int N)
		{
		if (N <= 0)
			throw new IllegalArgumentException (String.format
				("LinePoints(): N = %d illegal", N));
		this.N = N;
		}

// Exported operations.

	/**
	 * Get the number of points.
	 *
	 * @return  Number of points.
	 */
	public int size()
		{
		return N;
		}

	/**
	 * Determine if there are more points.
	 *
	 * @return  True if there are more points, false if not.
	 */
	public boolean hasNext()
		{
		return i < N;
		}

	/**
	 * Get the next point.
	 * <P>
	 * <I>Note:</I> The <TT>next()</TT> method is permitted to return the
	 * <I>same Point object</I>, with different coordinates, on every call.
	 * Extract the coordinates from the returned point object and store them in
	 * another data structure; do not store a reference to the returned point
	 * object itself.
	 *
	 * @return  Next point.
	 *
	 * @exception  NoSuchElementException
	 *     (unchecked exception) Thrown if there are no more points.
	 */
	public Point next()
		{
		if (i == N)
			throw new NoSuchElementException();
		p.x = i;
		p.y = 0.0;
		++ i;
		return p;
		}

	/**
	 * Unsupported operation.
	 */
	public void remove()
		{
		throw new UnsupportedOperationException();
		}

	}
