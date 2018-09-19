package com.nerdery.rtaza.jobtracker.data.util

import com.nerdery.rtaza.jobtracker.data.local.model.JobStatus
import com.nerdery.rtaza.jobtracker.data.local.model.JobTask
import com.nerdery.rtaza.jobtracker.data.remote.model.CustomerResponse
import com.nerdery.rtaza.jobtracker.data.remote.model.JobResponse
import com.nerdery.rtaza.jobtracker.data.remote.model.LocationResponse
import com.nerdery.rtaza.jobtracker.data.remote.model.WorkerResponse
import com.nerdery.rtaza.jobtracker.ui.util.TimeUtil
import java.util.concurrent.TimeUnit

class JobResponseFactory {

    companion object {

        fun createAcceptedHvacJobResponse() = JobResponse(
            id = 123456789,
            task = JobTask.HVAC.id,
            status = JobStatus.ACCEPTED.id,
            locationResponse = LocationResponse(
                "6501 Railroad Ave",
                "Twin Peaks",
                "98065",
                "WA",
                47.544390,
                -121.841370
            ),
            workerResponse = WorkerResponse(919345446, "Phillip", "Jeffries", true),
            customerResponse = CustomerResponse("Dale", "Cooper", "4306665421"),
            eta = TimeUtil.getCurrentTimeMillis() + TimeUnit.MINUTES.toMillis(45),
            customerNotes = "Diane, 7:30 am, February twenty-fourth. Entering town of Twin Peaks. Five miles south of the Canadian border, twelve miles west of the state line. Never seen so many trees in my life. As W.C. Fields would say, I'd rather be here than Philadelphia. It's fifty-four degrees on a slightly overcast day. Weatherman said rain. If you could get paid that kind of money for being wrong sixty percent of the time it'd beat working. Mileage is 79,345, gauge is on reserve, I'm riding on fumes here, I've got to tank up when I get into town. Remind me to tell you how much that is. Lunch was \$6.31 at the Lamplighter Inn. That's on Highway Two near Lewis Fork. That was a tuna fish sandwich on whole wheat, a slice of cherry pie and a cup of coffee. Damn good food. Diane, if you ever get up this way, that cherry pie is worth a stop."
        )

        fun createAcceptedElectricalJobResponse() = JobResponse(
            id = 987654321,
            task = JobTask.ELECTRICAL.id,
            status = JobStatus.ACCEPTED.id,
            locationResponse = LocationResponse(
                "137 W North Bend Way",
                "Twin Peaks",
                "98045",
                "WA",
                47.494310,
                -121.785130
            ),
            workerResponse = WorkerResponse(919937023, "Pete", "Martell", true),
            customerResponse = CustomerResponse("Laura", "Palmer", "4306652451"),
            eta = TimeUtil.getCurrentTimeMillis() + TimeUnit.MINUTES.toMillis(5),
            customerNotes = "Nowhere... fast. And you're not coming."
        )

        fun createEnRoutePlumbingJobResponse() = JobResponse(
            id = 543216789,
            task = JobTask.PLUMBING.id,
            status = JobStatus.EN_ROUTE.id,
            locationResponse = LocationResponse(
                "4200 Preston-Fall City Rd",
                "Twin Peaks",
                "98066",
                "WA",
                47.567310,
                -121.887550
            ),
            workerResponse = WorkerResponse(919012238, "Gordon", "Cole", true),
            customerResponse = CustomerResponse("Margaret", "Lanterman", "4306665421"),
            eta = TimeUtil.getCurrentTimeMillis() + TimeUnit.MINUTES.toMillis(1),
            customerNotes = "A log is a portion of a tree. (Turning end of log to camera.) At the end of a crosscut log — many of you know this — there are rings. Each ring represents one year in the life of the tree. How long it takes to a grow a tree! I don't mind telling you some things. Many things I, I musn't say. Just notice that my fireplace is boarded up. There will never be a fire there. On the mantelpiece, in that jar, are some of the ashes of my husband. My log hears things I cannot hear. But my log tells me about the sounds, about the new words. Even though it has stopped growing larger, my log is aware."
        )
    }
}