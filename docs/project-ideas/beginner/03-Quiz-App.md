# Quiz App

**Tier:** 1-Beginner  
**Stack:** Next.js Frontend, Spring Boot Backend for quiz storage

A quiz application where users answer multiple-choice questions and see their scores.

## Learning Goals

- Component composition and props
- State management with complex data structures
- Conditional rendering
- Form handling and validation
- Score calculation logic

## User Stories

- [ ] User can see the first quiz question and multiple answer options
- [ ] User can select an answer by clicking a button
- [ ] After selecting an answer, user sees if it's correct/incorrect with feedback
- [ ] User can proceed to the next question
- [ ] At the end, user sees their final score and percentage
- [ ] User can restart the quiz

## Bonus Features

- [ ] Display time taken to complete the quiz
- [ ] Show explanation for correct answers
- [ ] Allow user to review answered questions
- [ ] Shuffle answer options for each question
- [ ] (Spring Boot) Create `/api/quizzes` endpoint to store and retrieve quizzes
- [ ] Create `/api/results` endpoint to save user quiz attempts
- [ ] Show leaderboard with high scores

## Useful Links & Resources

- [React Hooks Best Practices](https://react.dev/reference/react)
- [Tailwind CSS Forms](https://tailwindcss.com/docs/plugins/forms)

## Implementation Hints

- Structure quiz data as an array of question objects
- Use useReducer for complex state management
- Consider accessibility (keyboard navigation)
- Think about difficulty levels or quiz categories
