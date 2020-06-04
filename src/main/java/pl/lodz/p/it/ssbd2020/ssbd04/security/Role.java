package pl.lodz.p.it.ssbd2020.ssbd04.security;

/**
 * Przechowuje stałe ciągi znakowe odpowiadające za identifykacje ról w kontenerze aplikacyjnym.
 */
public class Role {

    public static final String ADMIN = "ADMIN";
    public static final String MANAGER = "MANAGER";
    public static final String CUSTOMER_SERVICE = "CUSTOMER_SERVICE";
    public static final String CLIENT = "CLIENT";

    public static final String EditAccountAccessLevel = "editAccountAccessLevel";
    public static final String GetAccessLevels = "getAccessLevels";
    public static final String RetrieveOwnAccountDetails = "retrieveOwnAccountDetails";
    public static final String EditOwnAccountDetails = "editOwnAccountDetails";
    public static final String EditOtherAccountDetails = "editOtherAccountDetails";
    public static final String GetAllAccounts = "getAllAccounts";
    public static final String GetAllAccountsAuthInfo = "getAllAccountsAuthInfo";
    public static final String GetAccountAuthInfo = "getAccountAuthInfo";
    public static final String ChangeOwnAccountPassword = "changeOwnAccountPassword";
    public static final String ChangeOtherAccountPassword = "changeOtherAccountPassword";
    public static final String ChangeAccountActiveStatus = "changeAccountActiveStatus";
    public static final String RetrieveOtherAccountDetails = "retrieveOtherAccountDetails";
    public static final String ChangeRole = "changeRole";
    public static final String FindAccountsByName = "findAccountsByName";
    public static final String RefreshToken = "refreshToken";

    public static final String CreateSeatClass = "createSeatClass";
    public static final String DeleteSeatClass = "deleteSeatClass";
    public static final String UpdateSeatClass = "updateSeatClass";
    public static final String FindSeatClassByName = "findSeatClassByName";

    public static final String GetAllBenefits = "getAllBenefits";

    public static final String CreateAirplaneSchema = "createAirplaneSchema";
    public static final String UpdateAirplaneSchema = "updateAirplaneSchema";
    public static final String DeleteAirplaneSchema = "deleteAirplaneSchema";
    public static final String GetAllAirplaneSchemas = "getAllAirplaneSchemas";

    public static final String CreateAirport = "createAirport";
    public static final String DeleteAirport = "deleteAirport";
    public static final String UpdateAirport = "updateAirport";

    public static final String CreateConnection = "createConnection";
    public static final String DeleteConnection = "deleteConnection";
    public static final String UpdateConnection = "updateConnection";

    public static final String CreateFlight = "createFlight";
    public static final String CancelFlight = "cancelFlight";
    public static final String UpdateFlight = "updateFlight";
    public static final String GetTakenSeats = "getTakenSeats";
    public static final String CalculateConnectionProfit = "calculateConnectionProfit";

    public static final String FindTicketsByFlight = "findTicketsByFlight";
    public static final String FindTicketsByAccount = "findTicketsByAccount";
    public static final String FindTicketById = "findTicketById";
    public static final String GetAllTickets = "getAllTickets";
    public static final String GetOwnTickets = "getOwnTickets";
    public static final String CreateTicket = "createTicket";
    public static final String ReturnTicket = "returnTicket";
    public static final String UpdateTicket = "updateTicket";

    public static final String GenerateReport = "generateReport";

}
