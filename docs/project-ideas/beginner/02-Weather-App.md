# Weather App

**Tier:** 1-Beginner  
**Stack:** Next.js Frontend, Optional Spring Boot Backend for caching

A weather application that displays temperature, conditions, and day/night status for a given city using a public weather API.

## Learning Goals

- API integration and data fetching
- Environment variables for API keys
- Next.js Server Components vs Client Components
- Handling async data in React
- Responsive design with Tailwind CSS

## User Stories

- [ ] User can enter a city name in an input field
- [ ] Pressing Enter fetches and displays temperature, condition, and day/night indicator
- [ ] User can see a weather condition icon appropriate to the current weather
- [ ] Display feels-like temperature along with actual temperature

## Bonus Features

- [ ] Store last searched city in localStorage and auto-fetch on app load
- [ ] Show 5-day forecast
- [ ] Display humidity, wind speed, and UV index
- [ ] Allow user to switch between Celsius and Fahrenheit
- [ ] (Spring Boot) Create `/api/weather/{city}` endpoint to cache API calls in database
- [ ] Show search history with ability to switch between recent searches

## Useful Links & Resources

- [OpenWeatherMap API](https://openweathermap.org/api) (Free tier available)
- [Weather API](https://www.weatherapi.com/) (Generous free tier)
- [Next.js Data Fetching](https://nextjs.org/docs/app/building-your-application/data-fetching)
- [axios](https://axios-http.com/)

## Implementation Hints

- Use a free weather API with generous rate limits
- Consider using Next.js Server Components for initial data fetch
- Handle API errors gracefully with user-friendly messages
- Think about geolocation as a bonus feature
