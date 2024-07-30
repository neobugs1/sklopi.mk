import React from "react";
import { Box, Container, Flex, Heading, Link, Stack, Text, useColorModeValue, VStack } from "@chakra-ui/react";

function Home() {
  const bgColor = useColorModeValue("background", "background");
  const textColor = useColorModeValue("text", "text");
  const primaryColor = useColorModeValue("primary", "primary");
  const secondaryColor = useColorModeValue("secondary", "secondary");

  return (
    <>
      <Container maxW="1200px" py={8}>
        <Box
          className="home"
          bgImage="linear-gradient(rgba(98, 0, 234, 0.7), rgba(98, 0, 234, 0.7)),"
          bgSize="cover"
          bgPos="center"
          height="500px"
          display="flex"
          alignItems="center"
          justifyContent="center"
          color="white"
          textAlign="center"
          borderRadius="16px"
          overflow="hidden"
          position="relative"
        >
          <Box className="home-content" position="absolute" zIndex="1">
            <Heading as="h2">Склопи го својот совршен систем</Heading>
            <Text mb={6}>Изберете од илјадници компоненти и го составете го вашиот компјутер</Text>
            <Link
              href="/builder"
              bg="secondary"
              color="background"
              py={4}
              px={6}
              borderRadius="25px"
              fontWeight="600"
              transition="all 0.3s ease"
              border="2px solid transparent"
              _hover={{
                bg: "transparent",
                color: "secondary",
                borderColor: "secondary",
                px: 7,
              }}
            >
              Склопи
            </Link>
          </Box>
        </Box>
      </Container>
    </>
  );
}

export default Home;
