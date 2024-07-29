import { Box, Flex, Heading, Text, Link } from "@chakra-ui/react";

const Hero = () => (
  <Flex
    bgImage="url('https://images.unsplash.com/photo-1587202372616-b43abea06c2f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1200&q=80')"
    bgSize="cover"
    bgPos="center"
    height="400px"
    bg={"gray.800"}
    display="flex"
    alignItems="center"
    justifyContent="center"
    color="white"
    textAlign="center"
    borderRadius="8px"
    p={4}
  >
    <Box>
      <Heading as="h2">Customize Your Perfect PC</Heading>
      <Text>Choose from thousands of components and build your dream machine</Text>
      <Link href="/builder" bg="green.500" color="white" p={3} borderRadius="5px" mt={4} display="inline-block" _hover={{ bg: "green.600" }}>
        Start Building Now
      </Link>
    </Box>
  </Flex>
);

export default Hero;
