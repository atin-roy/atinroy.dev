# Calculator App

**Tier:** 1-Beginner  
**Stack:** Next.js Frontend, Optional Spring Boot Backend for history

A calculator application that supports basic arithmetic operations. This is a great way to understand UI/UX patterns, state management in React, and event handling.

## Learning Goals

- React hooks (`useState`, `useCallback`)
- Tailwind CSS styling
- Form input handling
- Complex state logic

## User Stories

- [ ] User can see a display showing the current number entered or result of last operation
- [ ] User can see a keypad with buttons for digits 0-9, operations (+, -, *, /), equals, clear (C), and clear all (AC)
- [ ] User can enter numbers as sequences up to 8 digits long
- [ ] User can perform basic arithmetic operations with proper precedence
- [ ] User can clear the last entry with 'C' or all with 'AC'
- [ ] User sees 'ERR' if any operation exceeds 8 digits

## Bonus Features

- [ ] Add +/- button to toggle sign of current number
- [ ] Support decimal point operations (up to 3 decimal places)
- [ ] Store calculation history using localStorage
- [ ] (Spring Boot) Create `/api/calculations` endpoint to save and retrieve calculation history from database
- [ ] Show previous calculations in a sidebar

## Useful Links & Resources

- [MDN Calculator Guide](https://developer.mozilla.org/en-US/docs/Web/API/Window)
- [React useState Hook](https://react.dev/reference/react/useState)
- [Tailwind CSS Documentation](https://tailwindcss.com/)

## Implementation Hints

- Avoid using `eval()` function - parse and calculate manually
- Consider a state machine approach for operation management
- Think about edge cases (division by zero, leading zeros)
