class GameError extends  Error {

    static class MarketError extends GameError {

        static class ItemNotAvailable extends MarketError {

        }

        static class CannotAffordItem extends MarketError {

        }

        static class FuelIsFull extends MarketError {

        }

        static class HealthIsFull extends MarketError {

        }

        static class NotEnoughSpace extends MarketError {

        }
    }

}
