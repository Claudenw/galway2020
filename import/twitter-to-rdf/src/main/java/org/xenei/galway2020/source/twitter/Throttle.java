package org.xenei.galway2020.source.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.RateLimitStatus;
import twitter4j.RateLimitStatusEvent;
import twitter4j.RateLimitStatusListener;

/**
 * A class to limit our requests to Twitter so that we don't overrun our limits.
 *
 */
public class Throttle implements RateLimitStatusListener {

	final static Logger LOG = LoggerFactory
			.getLogger(Throttle.class);
	
	private void debug(RateLimitStatus status)
	{
		LOG.debug( status.toString() );
		LOG.debug( "Limit: "+status.getLimit());
		LOG.debug( "Remaining: "+status.getRemaining());
		LOG.debug( "Reset time (S): "+status.getResetTimeInSeconds());
		LOG.debug( "(S) to reset: "+status.getSecondsUntilReset());
	}
	
	private void checkLimit( RateLimitStatus status )
	{
		if (LOG.isDebugEnabled())
		{
			debug( status );
		}

		if (status.getRemaining() <= 1)
		{
			try {
				long tm = 2000+(status.getSecondsUntilReset()*1000);
				LOG.info( String.format( "Sleeping for %s milliseconds", tm ));
				Thread.sleep( tm );
				LOG.info( "Woke up -- proccessing again");
			} catch (InterruptedException e) {
				LOG.warn( "Interruped" );
			}
		}
	}
	
	@Override
	public void onRateLimitStatus(RateLimitStatusEvent event) {
		checkLimit( event.getRateLimitStatus() );
	}

	@Override
	public void onRateLimitReached(RateLimitStatusEvent event) {
		checkLimit( event.getRateLimitStatus() );
	}
	
}