package ag.pinguin.issuetracker.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Utils class
 *
 * @author Mehdi Chitforoosh
 */
public class Utils {

    /**
     * Get a pageable instance
     *
     * @param startIndex   offset index
     * @param itemsPerPage items per page
     * @return
     */
    public static Pageable getPageable(int startIndex, int itemsPerPage) {
        int tempStartIndex = Math.max(startIndex, 0);
        int tempItemsPerPage = itemsPerPage > 0 ? itemsPerPage : 10;
        int pageNumber = tempStartIndex / tempItemsPerPage;
        return PageRequest.of(pageNumber, tempItemsPerPage);
    }

}
